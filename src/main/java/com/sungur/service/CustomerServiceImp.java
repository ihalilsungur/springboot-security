package com.sungur.service;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.sungur.model.Customer;
import com.sungur.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService {


    private CustomerRepository customerRepository;

    @Autowired
    CustomerServiceImp(
            CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public Optional<Customer> getByCustomer(int customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public List<Customer> allCustomers() {
        return customerRepository.findAll();
    }


    @Override
    public Customer saveOrUpdateCustomer(Customer customer) {
        if (customer == null) {
            customer = customerRepository.save(customer);
            return customer;
        } else {
            Optional<Customer> tempCustomer = customerRepository.findById(customer.getId());
            if (tempCustomer.isPresent()) {
               Customer newCustomer = tempCustomer.get();
                System.out.println(newCustomer);
                newCustomer.setId(customer.getId());
                newCustomer.setName(customer.getName());
                newCustomer.setSurname(customer.getSurname());
                newCustomer.setEmail(customer.getEmail());
                newCustomer = customerRepository.save(newCustomer);
                return newCustomer;
            } else {
                customer = customerRepository.save(customer);
                return customer;
            }
        }
    }


    @Override
    public void deleteCustomer(int id) throws Exception {
        Optional<Customer> tempCustomer = customerRepository.findById(id);
        if (tempCustomer.isEmpty()) {
            throw new Exception("Silinecek müşteri bulunamadı : " + id);
        }
        customerRepository.deleteById(id);
    }
}
