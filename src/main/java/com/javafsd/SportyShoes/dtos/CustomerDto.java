package com.javafsd.SportyShoes.dtos;

import lombok.Data;

@Data
public class CustomerDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String email;
    private String password;
}
