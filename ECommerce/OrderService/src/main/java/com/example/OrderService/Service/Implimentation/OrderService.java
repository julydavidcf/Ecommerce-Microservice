package com.example.OrderService.Service.Implimentation;

import com.example.OrderService.Dao.OrderRepository;
import com.example.OrderService.Entity.Order;
import com.example.OrderService.Payload.OrderDTO;
import com.example.OrderService.Service.OrderKafkaProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderKafkaProducerService kafkaProducerService;


    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        //order.setOrderId(UUID.randomUUID());
        order.setUserId(orderDTO.getUserId());
        order.setItemIds(orderDTO.getItemIds());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        OrderDTO savedOrder = convertToDTO(orderRepository.save(order));

        //String orderJson = objectMapper.writeValueAsString(savedOrder);
        kafkaProducerService.sendMessage(savedOrder);

        return savedOrder;
    }
    public OrderDTO getOrderById(String orderId) {
        Order order = orderRepository.findOrderByOrderId(orderId);
        return convertToDTO(order);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersByUserId(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderByOrderId(orderId);
        //kafkaProducerService.sendMessage("Order Deleted: " + orderId.toString());
    }

    public OrderDTO updateOrderStatus(String orderId, String newStatus) {
        Order order = orderRepository.findOrderByOrderId(orderId);
        order.setStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());

        Order updatedOrder = orderRepository.save(order);

        return convertToDTO(updatedOrder);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setUserId(order.getUserId());
        orderDTO.setItemIds(order.getItemIds());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setStatus(order.getStatus());
/*        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setUpdatedAt(order.getUpdatedAt());*/
        return orderDTO;
    }
}
