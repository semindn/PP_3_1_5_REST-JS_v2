package com.example.pp_3_1_2_crud_on_springboot.service;

import com.example.pp_3_1_2_crud_on_springboot.entity.User;
import java.util.List;

public interface UserService {

    List<User> getUsers();

    void addUser(User user);

    User getSingleUserById(Long id);

    void update(User user);

    void delete(Long id);

}
