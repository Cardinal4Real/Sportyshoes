package com.javafsd.SportyShoes.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="productcategory")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "categoryid")
    private Long categoryId;
    @Column(name = "categoryname")
    private String categoryName;
    @Lob
    @Column(name = "categoryimage")
    private String categoryImage;
    @OneToMany(mappedBy = "productCategory",fetch = FetchType.EAGER)
    private List<Product> listOfProducts;

    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryImage='" + categoryImage + '\'' +
                '}';
    }
}
