package com.example.spring_security.dao;

import com.example.spring_security.entity.Role;

import java.util.Set;

public interface RoleDao {

    Set<Role> getAllRoles();

    Role getRoleByName(String name);

    void addNewRole(Role nameOfNewRole);

}
