package com.makaia.HotelWorkshop.Controllers;

import com.makaia.HotelWorkshop.Modules.Customer;
import com.makaia.HotelWorkshop.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @PostMapping("/customer")
    public ResponseEntity<Customer> register(@RequestBody Customer customer){
        return customerService.create(customer);
    }
}
