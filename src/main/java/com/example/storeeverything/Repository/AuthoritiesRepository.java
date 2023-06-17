package com.example.storeeverything.Repository;

import com.example.storeeverything.data.Authorities;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuthoritiesRepository extends MongoRepository<Authorities,String> {
    List<Authorities> findByUser_Username(String Username);
    List<Authorities> findByUserId(String id);
}
