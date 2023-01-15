package com.example.spring_security.service;

import com.example.spring_security.dao.UserDao;
import com.example.spring_security.entity.Role;
import com.example.spring_security.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    //внедряем зависимость через конструктор
    private final UserDao userDao;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserDao userDao, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }


    @Override
    @Transactional
    public void updateExistingUser(User user) {
        User newUser = new User();
        newUser.setAge(user.getAge());
        newUser.setEnabled(true);
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setLogin(user.getLogin());
        //присваиваем объекту newUser роль USER если у объекта user, переданного в метод пустая роль
        //иначе перезаписываем имеющиеся в user роли в объект newUser
        if (user.getRoles().isEmpty()){
            newUser.addRole(roleService.getRoleByName("ROLE_USER"));
        } else {
            Set<Role> roles = user.getRoles();
            for (Role roleInSet : roles) {
                newUser.addRole(roleService.getRoleByName(roleInSet.getName()));
            }
        }
        newUser.setId(user.getId());
        // если пароль пришел пустой - его не меняли получаем хеш зашифрованного пароля по id объекта user
        // и устанавливаем его для newUser, иначе шифруем String и устанавливаем паролем получившийся хеш для newUser
        if (user.getPassword()==null) {
            newUser.setPassw(getSingleUserById(user.getId()).getPassword());
        } else {
            newUser.setPassw(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userDao.updateExistingUser(newUser);
    }

    @Override
    @Transactional
    public void createNewUser(User user) {
        if (userDao.getSingleUserByLogin(user.getLogin()) == null) {
            User newUser = new User();
            newUser.setAge(user.getAge());
            try {
                newUser.setEnabled(user.isEnabled());
            } catch (NullPointerException e){
                newUser.setEnabled(true);
            }
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setLogin(user.getLogin());
            //присваиваем объекту newUser роль USER если у объекта user, переданного в метод пустая роль
            //иначе перезаписываем имеющиеся в user роли в объект newUser
            if (user.getRoles().isEmpty()){
                newUser.addRole(roleService.getRoleByName("ROLE_USER"));
            } else {
                Set<Role> roles = user.getRoles();
                for (Role roleInSet : roles) {
                    newUser.addRole(roleService.getRoleByName(roleInSet.getName()));
                }
            }
            newUser.setPassw(bCryptPasswordEncoder.encode(user.getPassword()));
            userDao.createNewUser(newUser);
            System.out.println("***** Добавлен пользователь с логином" + user.getLogin());
        }
        else {
            System.out.println("***** Пользователь с логином %s уже существует" + user.getLogin());
        }
    }


    @Override
    public User getSingleUserById(Long id) {
        return userDao.getSingleUserById(id);
    }

    @Override
    public User getSingleUserByLogin(String login) {
        return userDao.getSingleUserByLogin(login);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional // для Lazy загрузки через связь сущностей
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userDao.getSingleUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с таким логином не найден");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }

}
