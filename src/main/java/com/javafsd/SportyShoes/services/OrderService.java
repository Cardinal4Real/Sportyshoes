package com.javafsd.SportyShoes.services;

import com.javafsd.SportyShoes.entities.Customer;
import com.javafsd.SportyShoes.entities.Order;
import com.javafsd.SportyShoes.entities.Product;
import com.javafsd.SportyShoes.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static java.lang.Long.sum;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public String addOrder(Order order){
        orderRepository.save(order);
        return "Order stored";
    }
    public String calcOrderNstore(Order order){
        if(order!=null){
            double totalPrice=order.getOrderQuantity() * order.getOrderPrice();
            order.setOrderTotal(totalPrice);
            orderRepository.save(order);
        }
        return "Order stored";
    }
    public double calcTotalOrder(List<Order> orderList) {
        return orderList.stream()
                .mapToDouble(Order::getOrderTotal)
                .sum();
    }

    public List<Order> findCustomerOrder(Customer customer) {
        return orderRepository.findAllByCustomer(customer);
    }
}
