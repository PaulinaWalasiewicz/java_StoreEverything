package com.example.storeeverything.controllers;
import com.example.storeeverything.data.User;
import com.example.storeeverything.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

//    Return all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    Find user by username for login
    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }

//    Return user by id
    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable String id) {
        return userRepository.findById(id).orElse(null);
    }

//    Create new user
//    TODO for now is Unauthorized probably due to spring security
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }


//    Delete user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
    }

}
