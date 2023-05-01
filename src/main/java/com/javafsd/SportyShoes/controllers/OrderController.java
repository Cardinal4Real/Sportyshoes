package com.javafsd.SportyShoes.controllers;

import com.javafsd.SportyShoes.entities.*;
import com.javafsd.SportyShoes.services.*;
import com.javafsd.SportyShoes.utilities.HelperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
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
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    CustomerLoginService customerLoginService;
    @Value("${sporty.page.returnv}")
    private String returnv;
    @Value("${sporty.page.returnv}")
    private String returnCV;

    @PostMapping("/placeOrder")
    public String placeOrder(@ModelAttribute("productID") String productid, HttpSession session, Model model) {
        String foundCustomer = helperClass.customerAuxilliary(session);
        if (!foundCustomer.isEmpty()) {
            Optional<Product> fProduct = productService.findOneProduct(productid);
            if (fProduct.isPresent() && session != null) {
                Customer sessioncustomer = (Customer) session.getAttribute("sessionuser");
                String cartQuantity = helperClass.cartHelper(session);
                Product product = fProduct.get();
                Order order = new Order();
                order.setCustomer(sessioncustomer);
                order.setProduct(product);
                order.setOrderPrice(product.getProductPrice());
                order.setOrderQuantity(1);
                model.addAttribute("Order", order);
                model.addAttribute("user", sessioncustomer.getEmail());
                model.addAttribute("prodCatalog", "active");
                model.addAttribute("cquantity", cartQuantity);
                model.addAttribute("pquantity", product.getProductQuantity());
                returnCV = "addOrder";
            } else {
                model.addAttribute("error", "Please sign in");
            }
        }
        return returnCV;
    }

    @PostMapping("/proc/addOrder")
    public String storeProduct(@ModelAttribute Order order, HttpSession session, Model model) {
        String addOrderResult = orderService.calcOrderNstore(order);
        String cartQuantity = helperClass.cartHelper(session);
        Customer customer=(Customer)session.getAttribute("sessionuser");
        List<Product> productList = productService.findAllProducts();

        String msg = addOrderResult.isEmpty() ? "" : "Order added to cart";
        model.addAttribute("msg", msg);
        model.addAttribute("user", customer.getEmail());
        model.addAttribute("prodCatalog", "active");
        model.addAttribute("ProductList", productList);
        model.addAttribute("cquantity", cartQuantity);
        return "viewProducts";
    }

    @GetMapping("/viewCart")
    public String viewCart(HttpSession session, Model model) {
        String foundCustomer = helperClass.customerAuxilliary(session);
        returnCV="signIn";
        if (!foundCustomer.isEmpty()) {
            Customer sessioncustomer = (Customer) session.getAttribute("sessionuser");
            Customer customer=customerLoginService.findCustomer(sessioncustomer);
            String cartQuantity = customer.getListOfOrders().size() > 0 ? String.valueOf(customer.getListOfOrders().size()) : "";
            double orderTotal = (customer.getListOfOrders().size() > 0) ? orderService.calcTotalOrder(customer.getListOfOrders()) : 0;
            model.addAttribute("user", customer.getEmail());
            model.addAttribute("orderList", customer.getListOfOrders());
            model.addAttribute("orderTotal", orderTotal);
            model.addAttribute("cquantity", cartQuantity);
            model.addAttribute("viewCart", "active");
            returnCV="viewCart";
        }
        return returnCV;
    }

    @GetMapping("/deleteOrder/{orderId}")
    public String deleteOrder(@PathVariable Long orderId, HttpSession session, Model model) {
        String view="signIn";
        String foundCustomer = helperClass.customerAuxilliary(session);
        if (!foundCustomer.isEmpty()) {
            orderService.deleteOrder(orderId);
            view = viewCart(session, model);
        }
        return view;
        }

    @PostMapping("/checkoutOrder")
    public String checkOutOrder(@RequestParam("orderTotal") double total, HttpSession session, Model model) {
        String msg="Payment received...Thanks for shopping with us";
        Optional<Customer> customerOpt = helperClass.findCustomer(session);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            List<Order> listOfCustomerOrder = orderService.findCustomerOrder(customer);
            if (listOfCustomerOrder.size() > 0) {
                List<Order> orderQtyCheck=
                listOfCustomerOrder.stream().filter(order->order.getOrderQuantity()>
                        productService.findOneProductQty(order.getProduct().getProductId())).collect(Collectors.toList());
                if(orderQtyCheck.isEmpty()){
/*                    listOfCustomerOrder.stream().forEach(order->productService.updateProductQty((order.getProduct()
                            .getProductId()),(productService.findOneProductQty(order.getProduct().getProductId())
                            -order.getOrderQuantity())));*/
                    for (Order order : listOfCustomerOrder) {
                        Long productId = order.getProduct().getProductId();
                        int updatedQty = productService.findOneProductQty(productId) - order.getOrderQuantity();
                        productService.updateProductQty(productId, updatedQty);
                    }

                    List<Order_CheckedOut> checkedOutList =
                        listOfCustomerOrder.stream().map(Order -> new Order_CheckedOut(Order)).collect(Collectors.toList());
                orderCheckedOutService.saveAllOrders(checkedOutList);
                orderService.deleteOrderList(listOfCustomerOrder);
                }else{
                    String orderNames = "";
                    for(Order a : orderQtyCheck) {
                        if(orderNames.isEmpty()) {
                            orderNames += a.getProduct().getProductName();
                        } else {
                            orderNames += ", " + a.getProduct().getProductName();
                        }
                    }
                    msg="Cannot fulfill order. Fewer items in stock. Kindly review"+ orderNames +"items in your shopping bag";
                }
            }
            System.out.printf("Total is %s", total);
            System.out.println(listOfCustomerOrder);
            System.out.println(listOfCustomerOrder.size());
            System.out.println(customer);
            model.addAttribute("msg", msg);
        }
        return "checkOutConfirmation";
    }

    @GetMapping("/viewReports")
    public String processOrders(HttpSession session, Model model) {
        String foundAdmin = helperClass.adminAuxilliary(session);
        if (!foundAdmin.isEmpty()) {
            List<ProductCategory> productCategoryList = productCategoryService.findAllProductCategory();
            System.out.println(productCategoryList);
            model.addAttribute("ProductCategory", productCategoryList);
            model.addAttribute("viewReports","active");
            returnv = "processOrders";
        }
        return returnv;
    }

    @GetMapping("/pendingOrders")
    public String pendingOrders(HttpSession session, Model model) {
        String foundAdmin = helperClass.adminAuxilliary(session);
        if (!foundAdmin.isEmpty()) {
            List<Order_CheckedOut> pendingOrders = orderCheckedOutService.findPendingOrders("Processing");
            model.addAttribute("pendingOrderList", pendingOrders);
            returnv = processOrders(session, model);
        }
        return returnv;
    }

    @PostMapping("/pendingOrdersDates")
    public String pendingOrdersDates(@RequestParam("selectedDate") String date,
                                     @RequestParam("selectedDateEnd") String dateEnd,
                                     @RequestParam("categoryoption") String catOption,
                                     HttpSession session, Model model) {
        String foundAdmin = helperClass.adminAuxilliary(session);
        if (!foundAdmin.isEmpty()) {
            try {
                LocalDateTime bDate = LocalDateTime.parse(date + "T00:00:00");
                LocalDateTime eDate = LocalDateTime.parse(dateEnd + "T23:59:59");
                List<Order_CheckedOut> pendingOrders = orderCheckedOutService.findAllByTimestampBetween(bDate, eDate);
                if (pendingOrders.size() > 0) {
                    List<Order_CheckedOut> pendingOrdersf = (!catOption.equals("None")) ? pendingOrders.stream()
                            .filter(orders -> orders.getProduct().getProductCategory().getCategoryName()
                                    .equals(catOption)).collect(Collectors.toList()) : pendingOrders;
                    model.addAttribute("pendingOrderList", pendingOrdersf);
                }
            } catch (Exception exception) {
                model.addAttribute("msg", "Error with request");
            }
            returnv = processOrders(session, model);
        }
        return returnv;
    }
}
