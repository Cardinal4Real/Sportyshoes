package com.javafsd.SportyShoes.controllers;

import com.javafsd.SportyShoes.dtos.SignInDto;
import com.javafsd.SportyShoes.entities.Customer;
import com.javafsd.SportyShoes.services.CustomerLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CustomerLoginController {
    @Autowired
    CustomerLoginService customerLoginService;
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
        Customer signInResult=customerLoginService.signIn(signInDto);
        if(signInResult==null){
            model.addAttribute("error","E-mail or password invalid");
            return "signIn";
        }else{
            session.setAttribute("sessionuser",signInResult);
            model.addAttribute("user",signInResult);
            System.out.println(signInResult);
            return "adminHome"; //for the time mean change to sportyshoeshome
        }
    }
    @GetMapping("/signInUser")
    public String signInUser(Model model){
        Customer customer =new Customer();
        model.addAttribute("user", customer);
        return "signIn";
    }
}
