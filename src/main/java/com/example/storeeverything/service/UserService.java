package com.example.storeeverything.service;

import com.example.storeeverything.Repository.UserRepository;
import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class UserService implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    public void createUsers(){
        System.out.println("Data creation started...");
        userRepository.save(new User(1,"User01","password1"));
        userRepository.save(new User(2,"User02","password2"));


        System.out.println("Data creation complete...");
    }
    public void showAllUsers(){
        userRepository.findAll().forEach(user -> System.out.println(user.getUsername()));
    }

    @Override
    public void run(String... args) throws Exception {
//        System.out.println("-------------CREATE GROCERY ITEMS------\n");
//        createUsers();
//
//        System.out.println("\n------------SHOW ALL GROCERY ITEMS---\n");
//        showAllUsers();

    }

}
