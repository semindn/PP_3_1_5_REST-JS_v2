package com.example.spring_security.controler;

import com.example.spring_security.entity.Role;
import com.example.spring_security.entity.User;
import com.example.spring_security.service.RoleService;
import com.example.spring_security.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    //внедряем зависимость через конструктор
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String helloPage() {
        return "login";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @RequestMapping("/login_error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/user")
    public String helloPage(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.getSingleUserByLogin(currentUser.getUsername());
        model.addAttribute("currentUser", user);
        return "user/user_panel";
    }
    @GetMapping(value = "/admin")
    public String index(ModelMap model, @AuthenticationPrincipal UserDetails authenticatedUser) {
        List<User> list = userService.getUsers();
        User currentUser = userService.getSingleUserByLogin(authenticatedUser.getUsername());
        System.out.println("текущий пользователь: "+currentUser);
        model.addAttribute("listUsers", list);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("newUser", new User());
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "admin/admin-panel";
    }

    @PostMapping("/admin/save_new_user")
    public String createNewUser(@ModelAttribute("newUser") User newUser,
                                           @RequestParam(value = "selectedRolesNewUser", required = false) String[] selectedRolesNewUser
    ){
        if (selectedRolesNewUser != null) {
            Set<Role> roles = new HashSet<>();
            for (String elemArrSelectedRoles : selectedRolesNewUser) {
                roles.add(roleService.getRoleByName(elemArrSelectedRoles));
            }
            newUser.setRoles(roles);
        }
        userService.createNewUser(newUser);
        return "redirect:/admin";
    }
    @PostMapping("/admin/update_exists_user")
    public String updateExistingUser(
            @ModelAttribute("user") User user,
            @RequestParam(value = "selectedRoles", required = false) String[] selectedRoles
    ){
        if (selectedRoles != null) {
            Set<Role> roles = new HashSet<>();
            for (String elemArrSelectedRoles : selectedRoles) {
                roles.add(roleService.getRoleByName(elemArrSelectedRoles));
            }
            user.setRoles(roles);
        }
        userService.updateExistingUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/delete_user")
    public String deleteUser (@RequestParam(value = "id") Long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
