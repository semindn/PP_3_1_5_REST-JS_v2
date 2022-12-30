package com.example.spring_security.dao;

import com.example.spring_security.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    void updateExistingUser(User user);

    void createNewUser(User user);

    User getSingleUserById(Long id);

    User getSingleUserByLogin(String login);

    void deleteUser(Long id);

}
