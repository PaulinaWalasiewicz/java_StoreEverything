package com.example.storeeverything.controllers;
import com.example.storeeverything.Repository.AuthoritiesRepository;
import com.example.storeeverything.Repository.UserRepository;
import com.example.storeeverything.data.Authorities;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.example.storeeverything.data.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthoritiesRepository authoritiesRepository;
    @GetMapping("/login")
    public String login(){return "login";}

    @GetMapping("/register")
    public String register(Model model){
        User user = new User();
        model.addAttribute("newUser",user);
        return "register";
    }
    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String regiatration(@Valid @ModelAttribute("newUser") User user ,BindingResult result,Model model){
        User existingUser = userRepository.findUserByUsername(user.getUsername());

        if(existingUser!= null){
            result.rejectValue("username",null,"Account with this username alredy exists");
        }
        if(result.hasErrors()){
            model.addAttribute("newUser",user);
            return "/register";
        }
        userRepository.save(user);

        Authorities authorities = new Authorities();
        authorities.setUser(user);
        authorities.setAuthority("USER");
        authoritiesRepository.save(authorities);
        return "redirect:/register?success";
    }
}
