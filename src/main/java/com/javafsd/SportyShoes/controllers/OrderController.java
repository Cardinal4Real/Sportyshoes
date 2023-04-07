package com.javafsd.SportyShoes.controllers;

import com.javafsd.SportyShoes.entities.Order;
import com.javafsd.SportyShoes.entities.Product;
import com.javafsd.SportyShoes.entities.ProductCategory;
import com.javafsd.SportyShoes.services.OrderService;
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
public class OrderController {
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    OrderService orderService;
    @GetMapping("/placeOrder")
    public String placeOrder(@ModelAttribute Order order,Model model){
        String addOrderResult=orderService.addOrder(order);
        model.addAttribute("msg","addOrderResult");
        model.addAttribute("Order",order);
        model.addAttribute("user","");
        return "addOrder";
    }
    @PostMapping("/proc/placedOrder")
    public String storeProduct(@ModelAttribute Order order, Model model){
        String storeOrderResult=orderService.addOrder(order);
        //model.addAttribute("msg","Logged in at"+ LocalDateTime.now());
        model.addAttribute("msg",storeOrderResult);
        model.addAttribute("Order",order);
        return "addOrder";
    }

/*    @GetMapping("/makePayment")
    public String orderPayment(Product product,Model model){
        List<Product> productList=productService.findAllProducts();
        String msg=productList.size()>0?"":"No products to display";
        System.out.println(productList);
        model.addAttribute("msg",msg);
        //model.addAttribute("user","");
        model.addAttribute("ProductList",productList);
        return "viewProducts";
    }*/
}
