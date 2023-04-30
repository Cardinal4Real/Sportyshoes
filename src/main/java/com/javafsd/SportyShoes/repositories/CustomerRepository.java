package com.javafsd.SportyShoes.repositories;

import com.javafsd.SportyShoes.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findFirstByEmail(String email);
    //public Customer findOneByLastName(String lastname);
    public List<Customer> findByEmailContaining(String email);
}
