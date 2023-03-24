package com.makaia.HotelWorkshop.Services;

import com.makaia.HotelWorkshop.Modules.Customer;
import com.makaia.HotelWorkshop.Repositories.CustomerRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    public ResponseEntity<Customer> create(Customer customer){
        //Exceptions
        if(customer.getDni() != null ){
            Optional<Customer> tempCustomer = this.customerRepository.findById(customer.getDni());
            if(tempCustomer.isPresent()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DNI is rejected in Database.");
            }
        }

        if(customer.getFirstName() != null && customer.getLastName() != null && customer.getDni() instanceof Integer){
            return new ResponseEntity<>(this.customerRepository.save(customer), HttpStatus.CREATED);
        }   else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DNI, FirstName and LastName are required.");
        }
    }
}
