package com.javafsd.SportyShoes.controllers;

import com.javafsd.SportyShoes.entities.Customer;
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

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @PostMapping("/placeOrder")
    public String placeOrder(@ModelAttribute("productID") String productid, HttpSession session, Model model){
        String npage="signIn";
        Optional<Product> fProduct=productService.findOneProduct(productid);
        if(fProduct.isPresent() && session!=null){
            Product product= fProduct.get();
            Order order=new Order();
            order.setCustomer((Customer)session.getAttribute("sessionuser"));
            order.setProduct(product);
            order.setOrderPrice(product.getProductPrice());
            order.setOrderQuantity(1);
            model.addAttribute("Order",order);
            npage= "addOrder";
        }else {
            //go sign in
            model.addAttribute("error","Please sign in");
        }
        return npage;
    }
    @PostMapping("/proc/addOrder")
    public String storeProduct(@ModelAttribute Order order, Model model,Product product){
        System.out.println("Order quantity "+order.getOrderQuantity());
        System.out.println("Trying to store product");
        String addOrderResult=orderService.calcOrderNstore(order);
        //System.out.println(order.getProduct().getProductName());
        List<Product> productList=productService.findAllProducts();
        String msg=addOrderResult.isEmpty()?"":"Order added to cart";
        model.addAttribute("msg",msg);
        model.addAttribute("ProductList",productList);
        return "viewProducts";
    }
    @GetMapping("/viewCart")
    public String viewCart(HttpSession session,Model model){
        Customer customer=((Customer)session.getAttribute("sessionuser"));
        //model.addAttribute("userDetails",customer);
        double orderTotal=orderService.calcTotalOrder(customer.getListOfOrders());
        model.addAttribute("user",customer.getEmail());
        model.addAttribute("orderList",customer.getListOfOrders());
        model.addAttribute("orderTotal",orderTotal);
        return "viewCart";
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
