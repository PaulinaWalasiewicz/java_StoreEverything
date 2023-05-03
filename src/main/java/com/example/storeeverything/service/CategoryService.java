package com.example.storeeverything.service;

import com.example.storeeverything.Repository.CategoryRepository;
import com.example.storeeverything.data.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements CommandLineRunner {
    @Autowired
    private CategoryRepository categoryRepository;

    public void createCategories(){
        System.out.println("Data creation started...");
        categoryRepository.save(new Category(1,"Home"));
        categoryRepository.save(new Category(2,"School"));
        categoryRepository.save(new Category(3,"Work"));
        System.out.println("Data creation complete...");

    }
    public void showAllCategories(){
        categoryRepository.findAll().forEach(category -> System.out.println(category.getName()));


    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("-------------CREATE GROCERY ITEMS------\n");
//        createCategories();
//
//        System.out.println("\n------------SHOW ALL GROCERY ITEMS---\n");
//        showAllCategories();
    }

}
