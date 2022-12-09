package com.example.pp_3_1_2_crud_on_springboot.controler;

import com.example.pp_3_1_2_crud_on_springboot.entity.User;
import com.example.pp_3_1_2_crud_on_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
//@RequestMapping("/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/")
    public String showAllUsers(ModelMap model) {
        List<User> list = userService.getUsers();
        model.addAttribute("listUsers", list);
        return "pages/index";
    }

    @GetMapping(value = "/show_single_user")
    public String showSingleUser (@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getSingleUserById(id));
        return "pages/show_user";
    }

    @GetMapping("/add_user")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        return "pages/add_user";
    }

    @PostMapping("/create_new_user")
    public String createNewUser(@ModelAttribute("user") User user){
        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping("/edit_user")
    public String edit(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getSingleUserById(id));
        System.out.println("****** Пользователь передан в модель для заполнения полей");
        return "pages/edit_user";
    }
    @PostMapping("/save_edit_user")
    public String updateUser(@ModelAttribute("user") User user) {
        System.out.println("****** Нажата кнопка сохранить изменения");
        userService.update(user);
        System.out.println("****** Пользователь сохранен");
        return "redirect:/";
    }

    @GetMapping(value = "/delete_user")
    public String deleteUser (@RequestParam(value = "id") Long id, Model model) {
        userService.delete(id);
        return "redirect:/";
    }

}
