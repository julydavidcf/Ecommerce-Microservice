package com.example.OrderService.Dao;

import com.example.OrderService.Entity.Order;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);

    @Query("{orderId:'?0'}")
    Order findOrderByOrderId(String id);

    @Query(value = "{orderId:'?0'}", delete = true)
    long deleteOrderByOrderId(String id);

    long count();
}