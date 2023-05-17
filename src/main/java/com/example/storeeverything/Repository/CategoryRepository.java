package com.example.storeeverything.Repository;

import com.example.storeeverything.data.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CategoryRepository extends MongoRepository<Category,String> {
    @Query("{ 'name' : ?0 }")
    Category findCategoryByName(String name);

    @Query("{'id': ?0}")
    Category findCategoryById(Integer id);

    Category findAllByOrderByNameAsc();
    Category findAllByOrderByNameDesc();
    public long count();

}

