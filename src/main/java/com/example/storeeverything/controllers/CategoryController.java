package com.example.storeeverything.controllers;

import com.example.storeeverything.Repository.CategoryRepository;
import com.example.storeeverything.data.Category;
import com.sun.source.tree.SynchronizedTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public String categories(Model model){
        return sortedCategories(model,"asc");
    }

    @GetMapping("/categories/s/")
    public String sortedCategories(Model model, @RequestParam(value = "sortDir",defaultValue = "asc") String sortDir){


        List<Category> categories = new ArrayList<>();
        if(sortDir.equals("asc")){
            categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC,"name"));
        }
        else {
            categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC,"name"));
        }
        model.addAttribute("categories",categories);
        model.addAttribute("sortDir",sortDir);
        String checkDirection = sortDir.equals("asc") ?"desc":"asc";
        model.addAttribute("checkDirection",checkDirection);
        return "categories";
    }
    @PostMapping("/addCategory")
    public  String addCategory(){
        return "addCategory";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category){
        categoryRepository.save(category);
        return "redirect:/";
    }

    @RequestMapping("/updateCategory/{id}")
    public String updateCategory(@PathVariable("id") String id,Model model){
        Category returnedCategory = categoryRepository.findById(id).get();
        model.addAttribute("any",returnedCategory);
        return "updateCategory";

    }
    @RequestMapping("/deleteCategroy/{id}")
    public String deleteCategory(@PathVariable String id){
        categoryRepository.delete(categoryRepository.findById(id).get());
        return "redirect:/";
    }

}
