package com.javafsd.SportyShoes.utilities;

import com.javafsd.SportyShoes.entities.Customer;
import com.javafsd.SportyShoes.services.CustomerLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;
@Component
public class HelperClass {
    @Autowired
    CustomerLoginService customerLoginService;
    public String cartHelper(HttpSession session){
        Optional<Customer> customerOpt=findCustomer(session);
        if(customerOpt.isPresent()){
            Customer customer=customerOpt.get();
            return customer.getListOfOrders().size()>0? String.valueOf(customer.getListOfOrders().size()) :"";
        }else{
            return "";
        }
    }
    public Optional<Customer> findCustomer(HttpSession session){
        Customer sessioncustomer=((Customer)session.getAttribute("sessionuser"));
        return Optional.ofNullable(customerLoginService.findCustomer(sessioncustomer));
    }
}
