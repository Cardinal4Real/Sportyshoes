package com.javafsd.SportyShoes.controllers;

import com.javafsd.SportyShoes.dtos.SignInDto;
import com.javafsd.SportyShoes.entities.Customer;
import com.javafsd.SportyShoes.entities.ProductCategory;
import com.javafsd.SportyShoes.services.CustomerLoginService;
import com.javafsd.SportyShoes.services.ProductCategoryService;
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
public class CustomerLoginController {
    @Autowired
    CustomerLoginService customerLoginService;
    @Autowired
    ProductCategoryService productCategoryService;
/*    @GetMapping("/")
    public String landingpage(SignUpDto signUpDto, Model model){
        model.addAttribute("SignUp", signUpDto);
        return "signUp";
    }*/
    @GetMapping("/")
    public String landingPage(){
        return "SignIn";
    }

    @GetMapping("/signUp")
    public String signUp(Customer customer, Model model){
        model.addAttribute("SignUp", customer);
        return "signUp";
    }
    @PostMapping("/signUp")
    public String signUpProc(@ModelAttribute Customer customer, Model model){
        String returnv="";
        String signUpResult=customerLoginService.signUp(customer);
        if(signUpResult.equals("SignUp successful")){
            model.addAttribute("error",signUpResult);
            returnv="signIn";
        }else {
            model.addAttribute("signUpResult",signUpResult);
            model.addAttribute("SignUp", customer);
            //model.addAttribute("SignUp", new SignUpDto());
            returnv="signUp";
        }
        return returnv;
    }
    @PostMapping("/signIn")
    public String signIn(@ModelAttribute SignInDto signInDto, Model model, HttpSession session){
        Customer customer=customerLoginService.signIn(signInDto);
        if(customer==null){
            model.addAttribute("error","E-mail or password invalid");
            return "signIn";
        }else{
            session.setAttribute("sessionuser",customer);
            String cartQuantity=customer.getListOfOrders().size()>0? String.valueOf(customer.getListOfOrders().size()) :"";
            List<ProductCategory> productCategoryList=productCategoryService.findAllProductCategory();
            model.addAttribute("user",customer.getEmail());
            model.addAttribute("cquantity",cartQuantity);
            model.addAttribute("productCategoryList",productCategoryList);
            return "sportyshoeshome";
        }
    }
    @GetMapping("/signInUser")
    public String signInUser(Model model){
        Customer customer =new Customer();
        model.addAttribute("user", customer);
        return "signIn";
    }
    @GetMapping("/signOut")
    public String signOut(HttpSession session){
        session.invalidate();
        return "signIn";
    }
    @GetMapping("/viewUsers")
    public String listCustomers(Model model){
        List<Customer> allUsers=customerLoginService.displayAllCustomers();
        model.addAttribute("allUsers",allUsers);
        return "viewUsers";
    }
    @PostMapping("/searchCustomer")
    public String searchCustomer(@ModelAttribute("email") String email, Model model){
        Optional<Customer> found= Optional.ofNullable(customerLoginService.findCustomerByEmail(email));
        if(found.isPresent()){
            System.out.println("customer found");
            Customer customerfound=found.get();
            model.addAttribute("found",customerfound);
        }
        return "viewUsers";
    }
}
