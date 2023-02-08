package com.example.spring_security.dto;

import com.example.spring_security.entity.Role;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private Integer age;

    private String login;

    private String passw;

    private String rolesToString;

    public String getRolesToString() {
        return rolesToString;
    }

    public void setRolesToString(String rolesToString) {
        this.rolesToString = rolesToString;
    }

    private Set<Role> roles = new HashSet<>();

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

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", login='" + login + '\'' +
                ", passw='" + passw + '\'' +
                ", roles=" + roles +
                '}';
    }
}
