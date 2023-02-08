package com.example.spring_security.configs;

import com.example.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    //внедряем зависимость через конструктор
    public WebSecurityConfig(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // указываем страницу с формой логина
                .loginPage("/login")
                .failureUrl("/login_error")
                // указываем хэндлер, содержащий в себе алгоритм действий при успешной аутентификации.
                // Например, тут мы можем отправить пользователя с ролью админа на админку после логина,
                // а с ролью юзер на главную страницу сайта и т.п.
                .successHandler(new LoginSuccessHandler())
                .loginProcessingUrl("/login")
                .permitAll();
        http.logout()
                // разрешаем делать логаут всем
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .and().csrf().disable();
        http.authorizeRequests()
                //страницы аутентификаци доступна всем
                .antMatchers("/login").anonymous()
                .antMatchers("/").anonymous()
                // защищенные URL
                .antMatchers("/admin/**").hasRole("ADMIN")
                //.antMatchers("/user").hasAnyRole("ADMIN", "USER")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .anyRequest()
                .authenticated();

//        Отключение авторизации для тестирования REST
//        http.authorizeRequests()
//                //страницы аутентификаци доступна всем
//                .antMatchers("/**").anonymous()
//                .anyRequest()
//                .authenticated();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

}