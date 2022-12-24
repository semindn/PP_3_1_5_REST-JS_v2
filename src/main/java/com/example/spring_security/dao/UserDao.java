package com.example.spring_security.dao;

import com.example.spring_security.entity.Role;
import com.example.spring_security.entity.User;

import java.util.List;
import java.util.Set;

public interface UserDao {

    List<User> getUsers();

    Set<Role> getAllRoles();

    void saveUser(User user);

    User getSingleUserById(Long id);

    User getSingleUserByLogin(String login);

    void deleteUser(Long id);

    Role getRoleByName(String name);


}
