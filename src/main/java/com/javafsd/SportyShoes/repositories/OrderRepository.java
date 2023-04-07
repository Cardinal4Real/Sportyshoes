package com.javafsd.SportyShoes.repositories;

import com.javafsd.SportyShoes.entities.Customer;
import com.javafsd.SportyShoes.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    public List<Order> findAllByCustomer(Customer customer);
}
