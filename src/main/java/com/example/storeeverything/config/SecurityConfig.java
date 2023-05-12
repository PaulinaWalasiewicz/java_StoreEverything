package com.example.storeeverything.config;

import com.example.storeeverything.SuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user1")
                .password("{noop}user1Pass")
                .authorities("ROLE_USER")
                .build();
        UserDetails admin = User.withUsername("admin1")
                .password("{noop}admin1Pass")
                .authorities("ROLE_ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .dispatcherTypeMatchers(HttpMethod.valueOf("/anonymous*"))
                .anonymous()
                .dispatcherTypeMatchers(HttpMethod.valueOf("/login*"))
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(myAuthenticationSuccessHandler())
                .failureUrl("/login.html?error=true")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                .key("uniqueAndSecret")
                .tokenValiditySeconds(86400)
                .and()
                .csrf()
                .disable();
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new SuccessHandler();   //my auth success handler method in the class
    }
}
