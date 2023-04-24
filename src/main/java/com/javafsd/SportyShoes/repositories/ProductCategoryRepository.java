package com.javafsd.SportyShoes.repositories;

import com.javafsd.SportyShoes.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
    public ProductCategory findOneByCategoryId(Long categoryID);
}
