//package com.example.storeeverything.badControllers;
//
//import com.example.storeeverything.Repository.CategoryRepository;
//import com.example.storeeverything.data.Category;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/category")
//public class CategoryController {
//    @Autowired
//    CategoryRepository categoryRepository;
//
//    @GetMapping
//    public List<Category> getAllCategories() {
//        return categoryRepository.findAll();
//    }
//
//    //TODO not working
//    @GetMapping("/id/{id}")
//    public Category getCategoryById(@PathVariable String id) {
//        return categoryRepository.findById(id).orElse(null);
//    }
//
//    @PostMapping
//    public Category createCategory(@RequestBody Category category) {
//        return categoryRepository.save(category);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteCategory(@PathVariable String id) {
//        categoryRepository.deleteById(id);
//    }
//
//}
