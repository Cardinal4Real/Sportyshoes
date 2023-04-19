package com.javafsd.SportyShoes.services;

import com.javafsd.SportyShoes.dtos.SignInDto;
import com.javafsd.SportyShoes.entities.AdminLogin;
import com.javafsd.SportyShoes.repositories.AdminLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AdminLoginService {
    @Autowired
    AdminLoginRepository adminLoginRepository;

    public AdminLogin signIn(SignInDto signInDto) {
/*        //Optional<AdminLogin> adminFound= Optional.ofNullable(adminLoginRepository.findOneByEmailAndPassword(signInDto.getEmail(), signInDto.getPassword()));
        if(adminFound.isPresent()){
            return "adminHome";
        }else{
            return "";
        }*/
        return adminLoginRepository.findOneByEmailAndPassword(signInDto.getEmail(), signInDto.getPassword());

    }

    public String changePwd(SignInDto signInDto) {
        AdminLogin adminLogin=adminLoginRepository.findOneByEmail(signInDto.getEmail());
        adminLogin.setPassword(signInDto.getPassword());
        adminLoginRepository.save(adminLogin);
        return "Password changed successfully";
    }
}
