package com.javafsd.SportyShoes.repositories;

import com.javafsd.SportyShoes.entities.Product;
import com.javafsd.SportyShoes.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    public List<Product> findAllByProductCategory(ProductCategory productCategory);

    @Modifying
    @Query("update Product u set u.productQuantity = :qty where u.productId = :id")
    void updateProductQty(@Param(value = "id") long productId, @Param(value = "qty") int productQuantity);
}
