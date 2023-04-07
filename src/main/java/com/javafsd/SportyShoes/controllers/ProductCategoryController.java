package com.javafsd.SportyShoes.controllers;

import com.javafsd.SportyShoes.entities.Product;
import com.javafsd.SportyShoes.entities.ProductCategory;
import com.javafsd.SportyShoes.services.ProductCategoryService;
import com.javafsd.SportyShoes.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductCategoryController {
    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping("/addCategory")
    public String addProductCategory(ProductCategory productCategory,Model model){
        model.addAttribute("ProductCategory",productCategory);
        return "addProductCategory";
    }
    @PostMapping("/proc/addProductCategory")
    public String storeProduct(@ModelAttribute ProductCategory productCategory, Model model){
        String addProductCategoryResult=productCategoryService.addProductCategory(productCategory);
        model.addAttribute("msg",addProductCategoryResult);
        model.addAttribute("ProductCategory",productCategory);
        return "addProductCategory";
    }

}
