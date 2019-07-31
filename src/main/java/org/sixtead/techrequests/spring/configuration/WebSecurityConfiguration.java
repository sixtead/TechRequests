package org.sixtead.techrequests.spring.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        String usersByUsername =
                "SELECT username, password_digest AS password, enabled " +
                "FROM users WHERE username = ?";
        String authoritiesByUsername =
                "SELECT users.username, roles.name " +
                "FROM users, roles, group_roles " +
                "WHERE users.username = ? " +
                "AND group_roles.group_id = users.group_id " +
                "AND roles.id = group_roles.role_id";

//        auth
//                .inMemoryAuthentication()
//                .withUser("user")
//                    .password(passwordEncoder.encode("user"))
//                    .roles("USER")
//                .and()
//                .withUser("admin")
//                    .password(passwordEncoder.encode("admin"))
//                    .roles("ADMIN");

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(usersByUsername)
                .authoritiesByUsernameQuery(authoritiesByUsername)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
//                .anyRequest().permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/groups/**").hasAnyRole("ADMIN")
                .antMatchers("/users/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
//                .httpBasic();
    }
}
