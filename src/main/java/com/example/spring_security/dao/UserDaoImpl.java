package com.example.spring_security.dao;

import com.example.spring_security.entity.User;
import javax.persistence.*;

import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    //внедряем зависимость
    @PersistenceContext
    private EntityManager entityManager;

    //внедряем зависимость через конструктор
    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void updateExistingUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void createNewUser(User user) {
        entityManager.persist(user);
    }


    @Override
    public User getSingleUserById(Long id) {
        TypedQuery<User> typedQuery = entityManager.createQuery("select u from User u where u.id = :id", User.class);
        typedQuery.setParameter("id", id);
        User user = typedQuery.getResultList().stream().findFirst().orElse(null);
        if (user!=null) user.setPassw(null);
        return user;
    }

    @Override
    public User getSingleUserByLogin(String login) {
        TypedQuery<User> typedQuery = entityManager.createQuery("select u from User u where u.login = :login", User.class);
        typedQuery.setParameter("login", login);
        return typedQuery.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        entityManager.remove(getSingleUserById(id));
    }
}
