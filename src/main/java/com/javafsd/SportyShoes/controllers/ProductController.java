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
public class ProductController {
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    ProductService productService;
    @GetMapping("/addProduct")
    public String addProduct(Product product,Model model){
        List<ProductCategory> productCategoryList=productCategoryService.findAllProductCategory();
        System.out.println(productCategoryList);
        model.addAttribute("Product",product);
        model.addAttribute("user","");
        model.addAttribute("ProductCategory",productCategoryList);
        return "addProduct";
    }
    @PostMapping("/proc/addProduct")
    public String storeProduct(@ModelAttribute Product product, Model model){
        String addProductResult=productService.addProduct(product);
        //model.addAttribute("msg","Logged in at"+ LocalDateTime.now());
        model.addAttribute("msg",addProductResult);
        model.addAttribute("Product",product);
        return "addProduct";
    }

    @GetMapping("/viewProduct")
    public String viewProduct(Product product,Model model){
        List<Product> productList=productService.findAllProducts();
        String msg=productList.size()>0?"":"No products to display";
        System.out.println(productList);
        model.addAttribute("msg",msg);
        //model.addAttribute("user","");
        model.addAttribute("ProductList",productList);
        return "viewProducts";
    }
}
