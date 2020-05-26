package com.sungur.service;

import com.sungur.excepiton.RecordNotFoundException;
import com.sungur.model.Employee;
import com.sungur.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService(
            @Qualifier("employeeRepository") EmployeeRepository employeeRepository){
        this.employeeRepository= employeeRepository;
    }

    public List<Employee> getAllEmployees()
    {
        List<Employee> result = (List<Employee>) employeeRepository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Employee>();
        }
    }

    public Employee getEmployeeById(Long id) throws RecordNotFoundException
    {
        Optional<Employee> employee = employeeRepository.findById(id);

        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    public Employee createOrUpdateEmployee(Employee entity)
    {
        if(entity.getId()  == null)
        {
            entity = employeeRepository.save(entity);
            return entity;
        }
        else
        {
            Optional<Employee> employee = employeeRepository.findById(entity.getId());

            if(employee.isPresent())
            {
                Employee newEntity = employee.get();
                newEntity.setEmail(entity.getEmail());
                newEntity.setFirstName(entity.getFirstName());
                newEntity.setLastName(entity.getLastName());

                newEntity = employeeRepository.save(newEntity);

                return newEntity;
            } else {
                entity = employeeRepository.save(entity);

                return entity;
            }
        }
    }

    public void deleteEmployeeById(Long id) throws RecordNotFoundException
    {
        Optional<Employee> employee = employeeRepository.findById(id);

        if(employee.isPresent())
        {
            employeeRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }
}
