package com.example.spring_security.service;

import com.example.spring_security.entity.Role;

import java.util.Set;

public interface RoleService {

    Set<Role> getAllRoles();

    Role getRoleByName(String name);

    void addNewRole(Role role);

}
