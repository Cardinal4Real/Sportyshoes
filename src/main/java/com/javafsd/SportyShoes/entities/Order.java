package com.javafsd.SportyShoes.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderid")
    private Long orderId;
    @ManyToOne
    @JoinColumn(name = "productid")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "customerid")
    private Customer customer;
    @Column(name = "orderquantity")
    private Integer orderQuantity;
    @Column(name = "orderprice")
    private Float orderPrice;
    @Column(name = "ordertotal")
    private Float orderTotal;

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", product=" + product.getProductName() +
                ", customer=" + customer.getEmail() +
                ", orderQuantity=" + orderQuantity +
                ", orderPrice=" + orderPrice +
                ", orderTotal=" + orderTotal +
                '}';
    }
}
