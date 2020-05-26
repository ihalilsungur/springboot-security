package com.sungur.controller;

import com.sungur.excepiton.RecordNotFoundException;
import com.sungur.model.Employee;
import com.sungur.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeService service;

    @RequestMapping(path = "/list")
    public String getAllEmployees(Model model) {
        List<Employee> list = service.getAllEmployees();
        model.addAttribute("employees", list);
        return "admin/employees";
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editEmployeeById(Model model, @PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException {
        if (id.isPresent()) {
            Employee entity = service.getEmployeeById(id.get());
            model.addAttribute("employee", entity);
        } else {
            model.addAttribute("employee", new Employee());
        }
        return "admin/add-edit-employee";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteEmployeeById(Model model, @PathVariable("id") Long id)
            throws RecordNotFoundException {
        service.deleteEmployeeById(id);
        return "redirect:/employees";
    }



    @RequestMapping(path = "/createEmployee",method = RequestMethod.POST)
    public String createOrUpdateEmployee(Employee employee) {
        service.createOrUpdateEmployee(employee);
        return "redirect:/employees/list";
    }
}
