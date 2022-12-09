package com.example.pp_3_1_2_crud_on_springboot.service;

import com.example.pp_3_1_2_crud_on_springboot.dao.UserDao;
import com.example.pp_3_1_2_crud_on_springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{


    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userDao.save(user);
    }

    @Override
    public User getSingleUserById(Long id) {
        return userDao.findById(id).orElseThrow(
                () -> new RuntimeException("Пользователя с таким ID не существует!!!")
        );
    }

    @Override
    @Transactional
    public void update(User user){
        userDao.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userDao.deleteById(id);
    }


}
