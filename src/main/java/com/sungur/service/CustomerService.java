package com.sungur.service;

import com.sungur.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    public Optional<Customer> getByCustomer(int customerId);

    public List<Customer> allCustomers();

    public Customer saveOrUpdateCustomer(Customer customer) throws Exception;

    public void deleteCustomer(int customer) throws Exception;
}
