package com.example.ecomerse.Dao;

import com.example.ecomerse.Entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends MongoRepository<Item, Long> {

    @Query("{name:'?0'}")
    Item findItemByName(String name);

    long count();
}
