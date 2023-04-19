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
    private int orderQuantity;
    @Column(name = "orderprice")
    private double orderPrice;
    @Column(name = "ordertotal")
    private double orderTotal;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

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
