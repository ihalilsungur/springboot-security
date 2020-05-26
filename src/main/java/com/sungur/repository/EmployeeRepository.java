package com.sungur.repository;

import com.sungur.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("employeeRepository")
public interface EmployeeRepository extends CrudRepository<Employee,Long> {
}
