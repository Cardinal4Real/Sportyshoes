package com.javafsd.SportyShoes.controllers;

import com.javafsd.SportyShoes.dtos.SignInDto;
import com.javafsd.SportyShoes.entities.AdminLogin;
import com.javafsd.SportyShoes.services.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class AdminLoginController {
    @Autowired
    AdminLoginService adminLoginService;
    @GetMapping("/admin/signIn")
    public String adminSignIn(){
        return "adminSignIn";
    }
    @GetMapping("/home")
    public String adminLandingPage(){
        return "adminHome";
    }

    @PostMapping("/proc/signIn")
    public String signIn(@ModelAttribute SignInDto signInDto, Model model, HttpSession session){
        AdminLogin signInResult=adminLoginService.signIn(signInDto);
        if(signInResult==null){
            model.addAttribute("error","E-mail or password invalid");
            return "adminsignIn";
        }else{
            //query all from category and assign to $ProductCategory
            //Product variable also needs to be populated
            model.addAttribute("user",signInResult);
            model.addAttribute("msg","Logged in at"+ LocalDateTime.now());
            session.setAttribute("sessionuser",signInResult);
            System.out.println(signInResult);
            return "adminHome";
        }
    }
}