package com.makaia.HotelWorkshop.Repositories;

import com.makaia.HotelWorkshop.Modules.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
