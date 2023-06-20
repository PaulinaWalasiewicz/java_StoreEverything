package com.example.storeeverything.controllers;


import com.example.storeeverything.Repository.AuthoritiesRepository;
import com.example.storeeverything.Repository.UserRepository;
import com.example.storeeverything.data.Authorities;
import com.example.storeeverything.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class Authoritycontroller {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthoritiesRepository authoritiesRepository;

    @GetMapping("/authorities")
    public String userList(Model model) {
        model.addAttribute("auth", authoritiesRepository.findAll());
        return "authorities";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam("id") String id) {

        Authorities au = authoritiesRepository.findById(id).get();
        userRepository.deleteById(au.getUser().getId());
        authoritiesRepository.deleteById(id);
        return "redirect:/authorities";
    }

    @PostMapping("/authorities/role")
    public String changeUserRole(@RequestParam("id") String id, @RequestParam("role") String role, RedirectAttributes redirectAttributes) {
        Authorities auth = authoritiesRepository.findById(id).orElse(null);
        if (auth != null) {
            auth.setAuthority(role);
            authoritiesRepository.save(auth);
            redirectAttributes.addFlashAttribute("successMessage", "User role updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update user role.");
        }
        return "redirect:/authorities";
    }
}
