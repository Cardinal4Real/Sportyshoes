package com.javafsd.SportyShoes.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customerid")
    private Long customerId;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "phonenumber")
    private String phoneNumber;
    @OneToMany (mappedBy="customer")
    private List<Order> listOfOrders;

    private String address;
    private String email;
    private String password;
    @CreationTimestamp
    private LocalDateTime timestamp;

/*    public Customer(CustomerDto customerDto) {
        this.firstName= customerDto.getFirstName();
        this.lastName= customerDto.getLastName();
        this.phoneNumber= customerDto.getPhoneNumber();
        this.address= customerDto.getAddress();
        this.email= customerDto.getEmail();
        this.password= customerDto.getPassword();
    }*/

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", listOfOrders=" + listOfOrders +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
