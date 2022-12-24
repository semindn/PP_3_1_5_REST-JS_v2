package com.example.spring_security.entity;

import com.example.spring_security.controler.UserController;
//import jakarta.persistence.*;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "313_v8_users", uniqueConstraints = @UniqueConstraint(columnNames = {"login"}))
public class User implements UserDetails{

//поля
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")  
    private Integer age;

    @Column(name = "login")
    private String login;

    @Column(name = "passw")
    private String passw;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "313_v8_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

//метод для добавления роли пользователю
    public void addRole(Role role) {
        this.roles.add(role);
    }

//конструкторы, гетеры и сетеры, toString
    public User() {
    }

    public User(String firstName, String lastName, Integer age, String login, String password, boolean enabled, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.login = login;
        this.passw = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String password) {
        this.passw = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", login='" + login + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }

// Имплементация методов UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role roleFromSet : roles) {
            //добавляем в список authorities наименования ролей, связанных с пользователем,
            //получаемые через метод getAuthority() интерфейса
            //org.springframework.security.core.GrantedAuthority, который реализовали в
            //сущности Role. Метод возвращает значние String поля Role.name
            authorities.add(new SimpleGrantedAuthority(roleFromSet.getAuthority()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return getPassw();
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
