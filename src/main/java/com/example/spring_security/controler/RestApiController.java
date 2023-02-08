package com.example.spring_security.controler;

import com.example.spring_security.dto.UserDTO;
import com.example.spring_security.entity.Role;
import com.example.spring_security.entity.User;
import com.example.spring_security.service.RoleService;
import com.example.spring_security.service.UserService;
import com.example.spring_security.util.EntityUserErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
//@RequestMapping("/api")
public class RestApiController {
    private final UserService userService;

    private final RoleService roleService;
    private final ModelMapper modelMapper;

    public RestApiController(UserService userService, RoleService roleService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    private User convertUserDtoToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertUserToUserDto(User user){
        UserDTO userDTO = new UserDTO();
        userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRolesToString(user.getRolesToOneLineString());
        return userDTO;
    }

    @GetMapping("/admin/api/list_users")
    public List<UserDTO> getAllUsers(){
        List<User> listUsers = userService.getUsers();
        List<UserDTO> listUsersDTO = new ArrayList<>();
        for (User user : listUsers){
            listUsersDTO.add(convertUserToUserDto(user));
        }
        return listUsersDTO;
    }

    @GetMapping("/admin/api/get_all_roles")
    public Set<Role> getAllExistsRoles(){
        return roleService.getAllRoles();
    }

    @GetMapping("/admin/api/user/{id}")
    public UserDTO getOneUser(@PathVariable("id") Long id){
        return convertUserToUserDto(userService.getSingleUserById(id));
    }
    //Если id не существует в сервисе вызовется исключение EntityUserNotFoundException из пакета util
    //и, перехватив его здесь (@ExceptionHandler), мы отсылаем в ответ объект EntityUserErrorResponse
    @ExceptionHandler
    private ResponseEntity<EntityUserErrorResponse> handleException() {
        EntityUserErrorResponse response = new EntityUserErrorResponse("User with this ID not found!!!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //404й статус
    }

    @GetMapping("/user/api/get_autorized_user")
    public UserDTO getCurrentUser(@AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.getSingleUserByLogin(currentUser.getUsername());
        return convertUserToUserDto(user);
    }

    @DeleteMapping("/admin/api/delete_user/{id}")
    public ResponseEntity<List<UserDTO>> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(getAllUsers());
    }

//    @PostMapping("/api/delete_user/{id}")
//    public void deleteUser(@PathVariable("id") Long id){
//        userService.deleteUser(id);
//    }

    @PostMapping("/admin/api/save_new_user")
    public List<UserDTO> createNewUser(@RequestBody UserDTO userDTO){
        System.out.println("Объект для добавления из json: " + userDTO);
        userService.createNewUser(convertUserDtoToUser(userDTO));
        return getAllUsers();
    }

    @PutMapping("/admin/api/update_exists_user")
    private ResponseEntity<List<UserDTO>> updateExistingUser(@RequestBody UserDTO userDTO) {
        System.out.println("Объект для изменения из json: " + userDTO);
        userService.updateExistingUser(convertUserDtoToUser(userDTO));
        return ResponseEntity.ok(getAllUsers());
    }



}
