package com.javafsd.SportyShoes.services;

import com.javafsd.SportyShoes.entities.Customer;
import com.javafsd.SportyShoes.entities.Order;
import com.javafsd.SportyShoes.entities.Order_CheckedOut;
import com.javafsd.SportyShoes.repositories.OrderRepository;
import com.javafsd.SportyShoes.repositories.Order_CheckedOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class Order_CheckedOutService {
    @Autowired
    Order_CheckedOutRepository orderCheckedOutRepository;

    public String saveAllOrders(List<Order_CheckedOut> listOfOrders){
        orderCheckedOutRepository.saveAll(listOfOrders);
        return "Orders moved";
    }

    public List<Order_CheckedOut> findPendingOrders(String status) {
        return orderCheckedOutRepository.findAllByOrderStatus(status);
    }
    public List<Order_CheckedOut> findAllByTimestampBetween(LocalDateTime firstTimestamp, LocalDateTime secondTimestamp) {
        return orderCheckedOutRepository.findAllByOrderTimeStampBetween(firstTimestamp,secondTimestamp);
    }

}
