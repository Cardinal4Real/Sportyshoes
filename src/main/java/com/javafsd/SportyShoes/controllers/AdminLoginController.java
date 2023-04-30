package com.javafsd.SportyShoes.controllers;

import com.javafsd.SportyShoes.dtos.SignInDto;
import com.javafsd.SportyShoes.entities.AdminLogin;
import com.javafsd.SportyShoes.entities.Customer;
import com.javafsd.SportyShoes.services.AdminLoginService;
import com.javafsd.SportyShoes.services.CustomerLoginService;
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
import java.util.Optional;

@Controller
public class AdminLoginController {
    @Autowired
    AdminLoginService adminLoginService;
    @Autowired
    CustomerLoginService customerLoginService;
    @Autowired
    OrderController orderController;
    @Autowired
    HelperClass helperClass;
    @Value("${sporty.page.returnv}")
    private String returnv;
    @GetMapping("/admin/signIn")
    public String adminSignIn(){
        //return "adminHome";
        return "adminSignIn";
    }

    @PostMapping("/proc/signIn")
    public String signIn(@ModelAttribute SignInDto signInDto, Model model, HttpSession session){
        System.out.println("trying to sign admin in");
        AdminLogin admin=adminLoginService.signIn(signInDto);
        System.out.println(signInDto.getPassword());
        returnv= "adminSignIn";
        if(admin==null){
            model.addAttribute("error","E-mail or password invalid");
        }else{
            System.out.println("Going to call orderController");
            model.addAttribute("user",admin);
            model.addAttribute("viewReports","active");
            session.setAttribute("adminsession",admin);
            returnv=orderController.processOrders(session,model);
        }
        return returnv;
    }
    @GetMapping("/changePassword")
    public String adminChangePwd(SignInDto signInDto,Model model,HttpSession session){
        String foundAdmin=helperClass.adminAuxilliary(session);
        if(!foundAdmin.isEmpty()){
            AdminLogin admin=(AdminLogin)session.getAttribute("adminsession");
            System.out.println("Admin present");
            signInDto.setEmail(admin.getEmail());
            signInDto.setPassword(admin.getPassword());
            model.addAttribute("SignInDto", signInDto);
            model.addAttribute("changePassword","active");
            returnv="changePwd";
        }
        return returnv;
    }

    @PostMapping("proc/changePwd")
    public String procPwdChange(@ModelAttribute SignInDto signInDto,Model model) {
        String resp=adminLoginService.changePwd(signInDto);
        model.addAttribute("SignInDto",new SignInDto());
        model.addAttribute("msg",resp);
        model.addAttribute("changePassword","active");
        return "adminSignIn";
    }
    @GetMapping("/viewUsers")
    public String listCustomers(Model model,HttpSession session){
        String foundAdmin=helperClass.adminAuxilliary(session);
        if(!foundAdmin.isEmpty()) {
            List<Customer> allUsers = customerLoginService.displayAllCustomers();
            model.addAttribute("allUsers", allUsers);
            model.addAttribute("viewUsers","active");
            returnv= "viewUsers";
        }
        return returnv;
    }
    @PostMapping("/searchCustomer")
    public String searchCustomer(@ModelAttribute("email") String email, Model model){
        List<Customer> found= customerLoginService.findCustomerByEmail(email);
        model.addAttribute("allUsers",found);
        model.addAttribute("viewUsers","active");
        return "viewUsers";
    }
}
