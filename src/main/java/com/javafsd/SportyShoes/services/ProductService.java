package com.javafsd.SportyShoes.services;

import com.javafsd.SportyShoes.entities.Product;
import com.javafsd.SportyShoes.entities.ProductCategory;
import com.javafsd.SportyShoes.repositories.ProductCategoryRepository;
import com.javafsd.SportyShoes.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public String addProduct(Product product){
        productRepository.save(product);
        return "Product stored";
    }
    @Transactional
    public void updateProductQty(Long productId,Integer qty){
        productRepository.updateProductQty(productId,qty);
    }
    public Optional<Product> findOneProduct(String id) {
        return productRepository.findById(Long.valueOf(id));
    }
    public int findOneProductQty(Long id) {
        Optional<Product> product= productRepository.findById(id);
        int qty=product.isPresent()?product.get().getProductQuantity():0;
        System.out.println("Quantity in stock is "+qty);
        return qty;
    }
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
    public List<Product> findAllProductsByCategory(ProductCategory productCategory) {
        return productRepository.findAllByProductCategory(productCategory);
    }
}
