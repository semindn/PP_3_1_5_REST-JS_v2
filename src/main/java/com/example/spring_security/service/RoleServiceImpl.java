package com.example.spring_security.service;

import com.example.spring_security.dao.RoleDao;
import com.example.spring_security.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{


    //внедряем зависимость через конструктор
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Set<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }

    @Override
    @Transactional
    public void addNewRole(Role role) {
        if (getRoleByName(role.getName()) == null) {
            roleDao.addNewRole(role);
            System.out.println(String.format("***** Добавлена роль %s", role.getName()));
        }
        else {
            System.out.println(String.format("***** Роль %s уже существует", role.getName()));
        }
    }
}
