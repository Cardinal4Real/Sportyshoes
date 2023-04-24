package com.javafsd.SportyShoes.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Orders_Checked_Out")
public class Order_CheckedOut {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderidpaid")
    private Long orderIdPaid;
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
    @CreationTimestamp
    private LocalDateTime orderTimeStamp;
    @Column(name = "orderstatus")
    private String orderStatus;
    @Column(name = "orderprocessedby")
    private String orderProcessedBy;
    @Column(name = "orderprocessedtimestamp")
    private LocalDateTime orderProcessedTimeStamp;

    public Order_CheckedOut(Order order) {
        this.product = order.getProduct();
        this.customer = order.getCustomer();
        this.orderQuantity = order.getOrderQuantity();
        this.orderPrice = order.getOrderPrice();
        this.orderTotal = order.getOrderTotal();
        this.orderStatus = "Processing";
        this.orderProcessedBy = "N/A";
    }
    public Order_CheckedOut() {
    }
    /*    public Long getOrderIdPaid() {
        return orderIdPaid;
    }

    public void setOrderIdPaid(Long orderIdPaid) {
        this.orderIdPaid = orderIdPaid;
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
    }*/

    @Override
    public String toString() {
        return "Order_CheckedOut{" +
                "orderIdPaid=" + orderIdPaid +
                ", product=" + product +
                ", customer=" + customer +
                ", orderQuantity=" + orderQuantity +
                ", orderPrice=" + orderPrice +
                ", orderTotal=" + orderTotal +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderProcessedBy='" + orderProcessedBy + '\'' +
                ", orderTimeStamp=" + orderTimeStamp +
                ", orderProcessedTimeStamp=" + orderProcessedTimeStamp +
                '}';
    }

}
