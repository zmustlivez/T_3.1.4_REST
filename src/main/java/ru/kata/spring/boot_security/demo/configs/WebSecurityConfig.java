package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
//    private UserServiceImpl userServiceImpl;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

/*    @Autowired
    public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
/*        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();*/
                http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
//                .antMatchers("/user-page/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/user/**").authenticated()
                .antMatchers("/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    // аутентификация inMemory
/*    @Bean
    @Override
    public UserDetailsService userDetailsService() {
*//*        UserDetails user =
                User.builder()//.withDefaultPasswordEncoder()
                        .username("user")
                        .password("{bcrypt}$2a$12$mjRBwKrNxmLSFbwq5/0iyuaP6Ks1GuAD4y2Q2TV6.h7KU7cwk53kO")//pass -user
                        .roles("USER")
                        .build();*//*
        UserDetails admin =
                User.builder()//.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("{bcrypt}$2a$12$0idhrAzxC6Juk6pQPXaxaOt2kHM8OSJ23UOcqp8UCNhgyFSm1ntQe")//pass - admin
                        .roles("USER", "ADMIN")
                        .build();
        return new InMemoryUserDetailsManager(admin);
    }*/


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}