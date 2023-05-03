package com.example.storeeverything.Repository;

import com.example.storeeverything.data.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User,String> {
    @Query("{'username': ?0}")
    User findUserByUsername(String username);

    public long count();
}
