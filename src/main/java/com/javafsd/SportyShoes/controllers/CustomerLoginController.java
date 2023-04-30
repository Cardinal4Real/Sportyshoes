package com.javafsd.SportyShoes.controllers;

import com.javafsd.SportyShoes.dtos.SignInDto;
import com.javafsd.SportyShoes.entities.Customer;
import com.javafsd.SportyShoes.entities.ProductCategory;
import com.javafsd.SportyShoes.services.CustomerLoginService;
import com.javafsd.SportyShoes.services.ProductCategoryService;
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
public class CustomerLoginController {
    @Autowired
    CustomerLoginService customerLoginService;
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    HelperClass helperClass;
    @Value("${sporty.page.returnv}")
    private String returnv;

    @GetMapping("/")
    public String landingPage(){
        return "SignIn";
    }
    @GetMapping("/home")
    public String custHome(HttpSession session, Model model){
        String foundCustomer = helperClass.customerAuxilliary(session);
        String returnCV = "signIn";
        if (!foundCustomer.isEmpty()) {
            Customer customer = (Customer) session.getAttribute("sessionuser");
            session.setAttribute("sessionuser",customer);
            returnCV= homeHelper(session,model,customer);
        }
            return returnCV;
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
            model.addAttribute("msg",signUpResult);
            returnv="signIn";
        }else {
            model.addAttribute("msg",signUpResult);
            model.addAttribute("SignUp", customer);
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
            return homeHelper(session,model,customer);
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
    public String homeHelper(HttpSession session, Model model, Customer customer){
        String cartQuantity=customer.getListOfOrders().size()>0? String.valueOf(customer.getListOfOrders().size()) :"0";
        List<ProductCategory> productCategoryList=productCategoryService.findAllProductCategory();
        model.addAttribute("user",customer.getEmail());
        model.addAttribute("cquantity",cartQuantity);
        model.addAttribute("prodCategories","active");
        model.addAttribute("productCategoryList",productCategoryList);
        return "sportyshoeshome";
    }
}
