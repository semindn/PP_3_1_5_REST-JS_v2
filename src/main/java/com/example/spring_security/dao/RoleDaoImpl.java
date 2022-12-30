package com.example.spring_security.dao;

import com.example.spring_security.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> getAllRoles() {
        return new HashSet<>(
                entityManager.createQuery("select r from Role r", Role.class).getResultList()
        );
    }

    @Override
    public Role getRoleByName(String name) {
        TypedQuery<Role> roleName = entityManager.createQuery("select r from Role r where r.name = :name", Role.class);
        roleName.setParameter("name", name);
        return roleName.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public void addNewRole(Role role) {
        entityManager.persist(role);
    }
}
