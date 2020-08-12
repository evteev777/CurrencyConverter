package ru.evteev.converter.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import ru.evteev.converter.service.UserService;

// Lombok
@RequiredArgsConstructor

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Value("${url.prefix}")
    private String prefix;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String root = (prefix == null || prefix.equals("/")) ? "/" : prefix + "/";
        String reg = root + "registration";
        String login = root + "login";
        String css = root + "css/*";
        String favicon = root + "images/favicon/*";

        http
            .authorizeRequests()
            .antMatchers(root, reg, login, css, favicon).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
            .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
