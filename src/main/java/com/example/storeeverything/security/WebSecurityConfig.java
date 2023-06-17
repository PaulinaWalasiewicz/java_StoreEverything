package com.example.storeeverything.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user =
//                User.withUsername("user1")
//                        .password("better")
//                        .roles("USER")
//                        .build();
//        UserDetails admin =
//                User.withUsername("admin")
//                        .password("thebest")
//                        .roles("ADMIN")
//                        .build();
//        System.out.println(user.getUsername()+" "+user.getPassword()+" "+user.getAuthorities());
//        System.out.println(admin.getUsername()+" "+admin.getPassword()+" "+admin.getAuthorities());
//        return new InMemoryUserDetailsManager(user,admin);
//
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
//        TODO: Resetrict some pages for USER or add some page for ADIMN
        http
                .authorizeHttpRequests((requests)->requests
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/register/save").permitAll()
                        .anyRequest().authenticated()
                ).formLogin((form)->form
                        .loginPage("/login")
                        .permitAll()
                ).logout((logout)->logout.logoutSuccessUrl("/"));
        return  http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        int rounds =12;
        return new BCryptPasswordEncoder(rounds);
//        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService customUserDetailsService){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        List<AuthenticationProvider> providers = List.of(authenticationProvider);
        return new ProviderManager(providers);
    }
}
