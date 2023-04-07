package com.javafsd.SportyShoes.services;

import com.javafsd.SportyShoes.entities.Customer;
import com.javafsd.SportyShoes.entities.Order;
import com.javafsd.SportyShoes.entities.Product;
import com.javafsd.SportyShoes.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @PostMapping("/addOrder")
    public String addOrder(Order order){
        orderRepository.save(order);
        return "Order stored";
    }

    public List<Order> findCustomerOrder(Customer customer) {
        return orderRepository.findAllByCustomer(customer);
    }
}