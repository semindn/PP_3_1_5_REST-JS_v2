package com.example.spring_security.configs;

import com.example.spring_security.entity.Role;
import com.example.spring_security.entity.User;
import com.example.spring_security.service.RoleService;
import com.example.spring_security.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class PostCounstructInitialization {

    private final UserService userService;

    private final RoleService roleService;

    public PostCounstructInitialization(@Lazy UserService userService, @Lazy RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void postConstruct(){

        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        roleService.addNewRole(roleAdmin);
        roleService.addNewRole(roleUser);

        User user1 = new User();
        user1.setFirstName("admin");
        user1.setLastName("admin");
        user1.setAge(31);
        user1.setLogin("admin");
        user1.setPassw("123");
        user1.setEnabled(true);
        Set<Role> rolesUser1 = new HashSet<>();
        rolesUser1.add(roleAdmin);
        user1.setRoles(rolesUser1);

        User user2 = new User();
        user2.setFirstName("user");
        user2.setLastName("user");
        user2.setAge(32);
        user2.setLogin("user");
        user2.setPassw("123");
        user2.setEnabled(true);
        Set<Role> rolesUser2 = new HashSet<>();
        rolesUser2.add(roleUser);
        user2.setRoles(rolesUser2);

        User user3 = new User();
        user3.setFirstName("user&admin");
        user3.setLastName("user&admin");
        user3.setAge(33);
        user3.setLogin("user&admin");
        user3.setPassw("123");
        user3.setEnabled(true);
        Set<Role> rolesUser3 = new HashSet<>();
        rolesUser3.add(roleUser);
        rolesUser3.add(roleAdmin);
        user3.setRoles(rolesUser3);

        userService.createNewUser(user1);
        userService.createNewUser(user2);
        userService.createNewUser(user3);

    }

}
