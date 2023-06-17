package com.example.storeeverything.service;

import com.example.storeeverything.Repository.AuthoritiesRepository;
import com.example.storeeverything.Repository.UserRepository;
import com.example.storeeverything.data.Authorities;
import com.example.storeeverything.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UsersAuthService implements UserDetailsService , CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthoritiesRepository authoritiesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username) != null?userRepository.findUserByUsername(username):null;
        if(user==null){
            throw new UsernameNotFoundException("User "+ username+ "not found !");
        }else {
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    authoritiesRepository.findByUserId(user.getId())
                            .stream()
                            .map(role->{
                                System.out.println("authorities "+ role.getAuthority());
                                return new SimpleGrantedAuthority(role.getAuthority());
                            })
                            .collect(Collectors.toSet()));
            return userDetails;
        }

    }
    //Create for existing users in db
    public void createAuth(){
        System.out.println("Data creation started...");
        authoritiesRepository.save(new Authorities("11",userRepository.findUserById("1"),"USER"));
        authoritiesRepository.save(new Authorities("12",userRepository.findUserById("2"),"ADMIN"));
        System.out.println("Data creation complete...");


    }
    @Override
    public void run(String... args) throws Exception {
//        System.out.println("-------------CREATE GROCERY ITEMS------\n");
//        createAuth();
//
//        System.out.println("\n------------SHOW ALL GROCERY ITEMS---\n");


    }
}
