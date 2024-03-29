package com.example.storeeverything.Repository;

import com.example.storeeverything.data.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category,String> {
    Category findCategoryByName(String name);

    @Query("{'id': ?0}")
    Category findCategoryById(String id);

    Category findAllByOrderByNameAsc();
    Category findAllByOrderByNameDesc();
    public long count();
}

