package com.javafsd.SportyShoes.controllers;

import com.javafsd.SportyShoes.entities.Product;
import com.javafsd.SportyShoes.entities.ProductCategory;
import com.javafsd.SportyShoes.services.CustomerLoginService;
import com.javafsd.SportyShoes.services.ProductCategoryService;
import com.javafsd.SportyShoes.services.ProductService;
import com.javafsd.SportyShoes.utilities.HelperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    ProductService productService;
    @Autowired
    HelperClass helperClass;

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
    public String viewProduct(Product product,HttpSession session,Model model){
        Map<String,String> mAttributes=new HashMap<>();
        String cartQuantity= helperClass.cartHelper(session);
        List<Product> productList=productService.findAllProducts();
        String msg=productList.size()>0?"":"No products to display";
        mAttributes.put("cquantity",cartQuantity);
        mAttributes.put("msg",msg);
        modelHelper(model,mAttributes);
        model.addAttribute("ProductList",productList);
        return "viewProducts";
    }
        @GetMapping("/viewProduct/{cId}")
        public String viewProduct(@PathVariable("cId") Long categoryId, Model model, HttpSession session){
        String cartQuantity=helperClass.cartHelper(session);
        ProductCategory productCategory=productCategoryService.findProductCategory(categoryId);
        List<Product> productListByCategory=productService.findAllProductsByCategory(productCategory);
        List<Product> productList=productService.findAllProducts();
        String msg=productList.size()>0?"":"No products to display";
        model.addAttribute("msg",msg);
        model.addAttribute("ProductListByCat",productListByCategory);
        model.addAttribute("ProductList",productList);
        model.addAttribute("cquantity",cartQuantity);
        return "viewProducts";
    }

        private Model modelHelper(Model model,Map<String,String> attributes){
        for(String key:attributes.keySet()){
           model.addAttribute(key,attributes.get(key));
        }
        return model;
    }
/*    private Model modelHelper(Model model,String ... attributes){
        for(String attribute:attributes){
            System.out.println(attribute);
           model.addAttribute(attribute,attribute);
        }
        return model;
    }*/
}
