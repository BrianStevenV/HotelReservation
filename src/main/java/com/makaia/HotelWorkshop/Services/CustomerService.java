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

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

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

    public Optional<Customer> researchById(int id){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are Rooms available now.");
        }
        return customer;
    }
    public List<Customer> researchAll(){
        List<Customer> customerList = (List<Customer>) customerRepository.findAll();
        if(customerList.isEmpty()){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are Rooms available now.");
        }
        return customerList;
    }
}
