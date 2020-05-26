package com.sungur.repository;

import com.sungur.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("customerRepository")
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

}
