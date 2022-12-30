package com.example.spring_security.service;

import com.example.spring_security.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  extends UserDetailsService {

    List<User> getUsers();

    void updateExistingUser(User user);

    void createNewUser(User user);

    User getSingleUserById(Long id);

    User getSingleUserByLogin(String login);

    void deleteUser(Long id);

}
