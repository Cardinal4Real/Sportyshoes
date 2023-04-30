package com.javafsd.SportyShoes.controllers;

import com.javafsd.SportyShoes.entities.Product;
import com.javafsd.SportyShoes.entities.ProductCategory;
import com.javafsd.SportyShoes.services.ProductCategoryService;
import com.javafsd.SportyShoes.services.ProductService;
import com.javafsd.SportyShoes.utilities.HelperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProductCategoryController {
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    HelperClass helperClass;
    @Value("${sporty.page.returnv}")
    private String returnv;

    @GetMapping("/addCategory")
    public String addProductCategory(ProductCategory productCategory, Model model, HttpSession session){
        String foundAdmin=helperClass.adminAuxilliary(session);
        if(!foundAdmin.isEmpty()) {
            model.addAttribute("ProductCategory", productCategory);
            model.addAttribute("createCategory", "active");
            returnv= "addProductCategory";
        }
        return returnv;
    }
    @PostMapping("/proc/addProductCategory")
    public String storeProduct(@ModelAttribute ProductCategory productCategory, Model model){
        String addProductCategoryResult=productCategoryService.addProductCategory(productCategory);
        model.addAttribute("msg",addProductCategoryResult);
        model.addAttribute("ProductCategory",productCategory);
        model.addAttribute("createCategory", "active");
        return "addProductCategory";
    }

}
