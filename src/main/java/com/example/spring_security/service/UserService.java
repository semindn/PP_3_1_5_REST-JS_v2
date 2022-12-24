package com.example.spring_security.service;

import com.example.spring_security.entity.Role;
import com.example.spring_security.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService  extends UserDetailsService {

    List<User> getUsers();

    Set<Role> getAllRoles();

    void saveUser(User user);

    User getSingleUserById(Long id);

    User getSingleUserByLogin(String login);

    void deleteUser(Long id);

    public Role getRoleByName(String name);

}
