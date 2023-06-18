package com.example.storeeverything.Repository;

import com.example.storeeverything.data.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface CategoryRepository extends MongoRepository<Category,String> {

    @Query("{ 'name' : ?0 }")
    Category findCategoryByName(String name);

    @Query("{'id': ?0}")
    Category findCategoryById(String id);

    @Autowired
    Category findAllByOrderByNameAsc();
    @Autowired
    Category findAllByOrderByNameDesc();
    long count();
}

