package com.javafsd.SportyShoes.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.nio.MappedByteBuffer;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productid")
    private Long productId;
    @Column(name = "productname")
    private String productName;
    @ManyToOne
    @JoinColumn(name="categoryid")
    private ProductCategory productCategory;
    @Column(name = "productquantity")
    private int productQuantity;
    @Column(name = "productprice")
    private double productPrice;
    @Column(name = "productdescription")
    private String productDescription;
    @Lob
    @Column(name = "productimage")
    private String productImage;
    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
    private List<Order> listOfOrders;
/*    @Column(name="productdiscountpercentage")
    private float productDiscountPercentage;
    @Column(name="productdiscountamount")
    private float productDiscountAmount;
    @Column(name="productdiscountedprice")
    private float productDiscountedPrice;*/
@Override
public String toString() {
    return "Product{" +
            "productId=" + productId +
            ", productName='" + productName + '\'' +
            ", productCategory=" + productCategory.getCategoryName() +
            ", productQuantity=" + productQuantity +
            ", productPrice=" + productPrice +
            ", productDescription='" + productDescription + '\'' +
            ", productImage='" + productImage + '\'' +
            //", listOfOrders=" + listOfOrders +
            '}';
}
}
