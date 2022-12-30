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
        return "index";
    }
    @GetMapping("/user")
    public String helloPage(Model model, @AuthenticationPrincipal UserDetails curUser) {
        User user = userService.getSingleUserByLogin(curUser.getUsername());
        model.addAttribute("user", user);
        return "user/curr_user_info";
    }
    @GetMapping(value = "/admin")
    public String index(ModelMap model) {
        List<User> list = userService.getUsers();
        model.addAttribute("listUsers", list);
        return "admin/list_of_users";
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



    @GetMapping(value = "/admin/list_users")
    public String showAllUsers(ModelMap model) {
        List<User> list = userService.getUsers();
        model.addAttribute("listUsers", list);
        return "admin/list_of_users";
    }

    @GetMapping(value = "/admin/show_single_user")
    public String showSingleUser (@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getSingleUserById(id));
        return "admin/show_single_user_info";
    }

    @GetMapping("/admin/add_user")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/form_add_user";
    }

    @GetMapping("/admin/edit_user")
    public String edit(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getSingleUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/form_edit_user";
    }

    @PostMapping("/admin/save_or_update_user")
    public String saveNewOrUpdateExistUser(@ModelAttribute("user") User user,
                                @RequestParam(value = "selectedRoles", required = false) String[] selectedRoles
                                 ){
        if (selectedRoles != null) {
            Set<Role> roles = new HashSet<>();
            for (String elemArrSelectedRoles : selectedRoles) {
                roles.add(roleService.getRoleByName(elemArrSelectedRoles));
            }
            user.setRoles(roles);
        }
        if (user.getId() == null) {
            userService.createNewUser(user);
        } else {
            userService.updateExistingUser(user);
        }
        return "redirect:/admin";
    }


    @GetMapping(value = "/admin/delete_user")
    public String deleteUser (@RequestParam(value = "id") Long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
