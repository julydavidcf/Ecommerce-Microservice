package com.example.ecomerse.Dao;

import com.example.ecomerse.Entity.Item;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends MongoRepository<Item, Long> {

    @Query("{category:'?0'}")
    List<Item> findItemsByCategory(String category);

    @Query("{itemId:'?0'}")
    Item findItemByItemId(String id);

    @Query(value = "{itemId:'?0'}", delete = true)
    long deleteItemByItemId(String id);

    long count();
}
