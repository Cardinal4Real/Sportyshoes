package com.javafsd.SportyShoes.controllers;

import com.javafsd.SportyShoes.entities.*;
import com.javafsd.SportyShoes.services.*;
import com.javafsd.SportyShoes.utilities.HelperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    Order_CheckedOutService orderCheckedOutService;
    @Autowired
    ProductService productService;
    @Autowired
    HelperClass helperClass;
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
    public String storeProduct(@ModelAttribute Order order, HttpSession session, Model model){
        String addOrderResult=orderService.calcOrderNstore(order);
        String cartQuantity= helperClass.cartHelper(session);
        List<Product> productList=productService.findAllProducts();
        String msg=addOrderResult.isEmpty()?"":"Order added to cart";
        model.addAttribute("msg",msg);
        model.addAttribute("ProductList",productList);
        model.addAttribute("cquantity",cartQuantity);
        return "viewProducts";
    }
    @GetMapping("/viewCart")
    public String viewCart(HttpSession session,Model model){
        Optional<Customer> customerOpt=helperClass.findCustomer(session);
        if(customerOpt.isPresent()){
            Customer customer=customerOpt.get();
            String cartQuantity=customer.getListOfOrders().size()>0? String.valueOf(customer.getListOfOrders().size()) :"";
            double orderTotal=(customer.getListOfOrders().size()>0)?orderService.calcTotalOrder(customer.getListOfOrders()):0;
            model.addAttribute("user",customer.getEmail());
            model.addAttribute("orderList",customer.getListOfOrders());
            model.addAttribute("orderTotal",orderTotal);
            model.addAttribute("cquantity",cartQuantity);
        }
        return "viewCart";
    }
    @GetMapping("/deleteOrder/{orderId}")
    public String deleteOrder(@PathVariable Long orderId,HttpSession session, Model model){
        orderService.deleteOrder(orderId);
        String view=viewCart(session,model);
        return view;
    }

   @PostMapping("/checkoutOrder")
    public String checkOutOrder(@RequestParam("orderTotal") double total, HttpSession session, Model model){
       Optional<Customer> customerOpt=helperClass.findCustomer(session);
       if(customerOpt.isPresent()){
           Customer customer=customerOpt.get();
           List<Order> listOfCustomerOrder=orderService.findCustomerOrder(customer);
           if(listOfCustomerOrder.size()>0){
              List<Order_CheckedOut> checkedOutList =
                      listOfCustomerOrder.stream().map(Order->new Order_CheckedOut(Order)).collect(Collectors.toList());
                orderCheckedOutService.saveAllOrders(checkedOutList);
                orderService.deleteOrderList(listOfCustomerOrder);
           }
       System.out.printf("Total is %s",total);
           System.out.println(listOfCustomerOrder);
           System.out.println(listOfCustomerOrder.size());
       System.out.println(customer);
       model.addAttribute("msg","Payment received...Thanks for shopping with us");
       }
       return "checkOutConfirmation";
   }

    @GetMapping("/processOrders")
    public String processOrders(HttpSession session, Model model){
        //orderService
        //String view=viewCart(session,model);
        return "processOrders";
    }

    @GetMapping("/pendingOrders")
    public String pendingOrders(HttpSession session, Model model){
        //orderService
        //String view=viewCart(session,model);
        System.out.println("Pending Orders invoked");
        List<Order_CheckedOut> pendingOrders=orderCheckedOutService.findPendingOrders("Processing");
        System.out.println(pendingOrders);
        model.addAttribute("pendingOrderList",pendingOrders);
        return "processOrders";
    }
}
