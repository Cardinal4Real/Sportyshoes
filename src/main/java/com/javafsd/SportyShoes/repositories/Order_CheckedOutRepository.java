package com.javafsd.SportyShoes.repositories;

import com.javafsd.SportyShoes.entities.Order_CheckedOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface Order_CheckedOutRepository extends JpaRepository<Order_CheckedOut,Long> {
    public List<Order_CheckedOut> findAllByOrderStatus(String status);
    public List<Order_CheckedOut> findAllByOrderTimeStampBetween(LocalDateTime timestamp1, LocalDateTime timestamp2);

}
