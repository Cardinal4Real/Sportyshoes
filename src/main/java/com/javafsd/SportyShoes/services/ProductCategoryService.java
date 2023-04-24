package com.javafsd.SportyShoes.services;

import com.javafsd.SportyShoes.entities.Product;
import com.javafsd.SportyShoes.entities.ProductCategory;
import com.javafsd.SportyShoes.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductCategoryService{
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    public String addProductCategory(ProductCategory productCategory){
       productCategoryRepository.save(productCategory);
        return "Category details stored";
    }

    public List<ProductCategory> findAllProductCategory() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory findProductCategory(Long id) {
        return productCategoryRepository.findOneByCategoryId(id);
    }
}
