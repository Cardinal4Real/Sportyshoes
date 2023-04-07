package com.javafsd.SportyShoes.repositories;

import com.javafsd.SportyShoes.entities.AdminLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminLoginRepository extends JpaRepository<AdminLogin,Long> {
    public AdminLogin findOneByEmailAndPassword(String email, String password);
}
