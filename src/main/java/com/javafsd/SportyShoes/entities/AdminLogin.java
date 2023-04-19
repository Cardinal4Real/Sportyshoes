package com.javafsd.SportyShoes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="adminlogin")
public class AdminLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;


/*    public AdminSignInEntity(SignUpDto signUpDto) {
        this.firstName= signUpDto.getFirstName();
        this.lastName= signUpDto.getLastName();
        this.phoneNumber= signUpDto.getPhoneNumber();
        this.address= signUpDto.getAddress();
        this.email=signUpDto.getEmail();
        this.password=signUpDto.getPassword();
    }*/
}
