package com.example.springboot;

import com.example.springboot.models.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public  interface UserRepository extends MongoRepository<User, String> {


    public User findByEmail(String email);

    public User findByid(String id);

}
