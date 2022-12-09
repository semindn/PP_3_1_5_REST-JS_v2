package com.example.pp_3_1_2_crud_on_springboot.dao;

import com.example.pp_3_1_2_crud_on_springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {

//    List<User> getUsers();
//
//    void addUser(User user);
//
//    User getSingleUserById(Long id);
//
//    void update(User user);
//
//    void delete(Long id);
//    @Query()
//    List<User> asdasd(Long id);

}
