package com.javafsd.SportyShoes.services;

import com.javafsd.SportyShoes.entities.Product;
import com.javafsd.SportyShoes.entities.ProductCategory;
import com.javafsd.SportyShoes.repositories.ProductCategoryRepository;
import com.javafsd.SportyShoes.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public String addProduct(Product product){
        productRepository.save(product);
        return "Product stored";
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}