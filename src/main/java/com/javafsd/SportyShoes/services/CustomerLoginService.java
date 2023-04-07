package com.javafsd.SportyShoes.services;

import com.javafsd.SportyShoes.dtos.SignInDto;
import com.javafsd.SportyShoes.entities.Customer;
import com.javafsd.SportyShoes.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerLoginService {
    @Autowired
    CustomerRepository customerRepository;

    public String signUp(Customer customer) {
        Customer findEmail=(customer !=null)? customerRepository.findFirstByEmail(customer.getEmail()):null;
        Customer saveEntity=(findEmail==null)? customerRepository.save(customer):null;
        return (saveEntity==null)?"Email already exists":"SignUp successful";
    }

    public Customer signIn(SignInDto signInDto) {
        Customer findSignInEntity=(signInDto!=null)? customerRepository.findFirstByEmail(signInDto.getEmail()):null;
        if(findSignInEntity!=null){
            return findSignInEntity.getPassword().equals(signInDto.getPassword())?findSignInEntity:null;
        }else{
            return null;
        }
    }
}
