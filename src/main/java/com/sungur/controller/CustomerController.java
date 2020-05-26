package com.sungur.controller;

import com.sungur.excepiton.RecordNotFoundException;
import com.sungur.model.Customer;
import com.sungur.model.User;
import com.sungur.service.CustomerService;
import com.sungur.service.UserService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.print.attribute.standard.PresentationDirection;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {


    private UserService userService;
    private CustomerService customerService;


    @Autowired
    public CustomerController(UserService userService, CustomerService customerService) {
        this.userService = userService;
        this.customerService = customerService;

    }


    @GetMapping(value = "new-customer")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        Customer customer = new Customer();
        modelAndView.addObject("customer", customer);
        modelAndView.setViewName("user/new-customer");
        return modelAndView;
    }

    @GetMapping("customer")
    public ModelAndView allCustomers() {
        ModelAndView modelAndView = new ModelAndView();
        List<Customer> customers = customerService.allCustomers();
        modelAndView.addObject("customer", customers);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getUserName());
        modelAndView.addObject("customerMessage", "Content Available Only for Users withUser Role");
        modelAndView.setViewName("user/customer");
        return modelAndView;
    }


    @PostMapping("new-customer")
    public ModelAndView createCustomer(Customer customer, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/new-customer");
        } else {
            try {
                customerService.saveOrUpdateCustomer(customer);
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
            modelAndView.addObject("successMessage", "Müşteri başarılı bir şekilde oluşturuldu.");
            modelAndView.addObject("customer", new Customer());
            modelAndView.setViewName("redirect:/customer");
        }
        return modelAndView;
    }

    @GetMapping("/customer/{id}")
    public ModelAndView deleteCustomer(@PathVariable("id") int id) throws Exception {
        Optional<Customer> customer = customerService.getByCustomer(id);
        if (customer.isEmpty()) {
            throw new Exception("Silinecek müşteri bulanamadı ?" + customer);
        }
        ModelAndView modelAndView = new ModelAndView();
        customerService.deleteCustomer(id);
        modelAndView.addObject("customer", customerService.allCustomers());
        modelAndView.setViewName("/user/customer");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView updateCustomer(@PathVariable("id") int id) throws RecordNotFoundException {
        Optional<Customer> customer = customerService.getByCustomer(id);
        ModelAndView modelAndView = new ModelAndView();
        if (customer.isPresent()) {
            modelAndView.addObject("customer",customer);
        }
        modelAndView.setViewName("/user/new-customer");
        return modelAndView;
    }

}
