package com.example.spring_security.entity;

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
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
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

    //конструкторы
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

    //гетеры и сетеры
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

    //переопределение toString, equals и hashcode
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled && Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(age, user.age) && Objects.equals(login, user.login) && Objects.equals(passw, user.passw) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age, login, passw, enabled, roles);
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
