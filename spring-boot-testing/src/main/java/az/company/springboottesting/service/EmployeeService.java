package az.company.springboottesting.service;

import az.company.springboottesting.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee save(Employee employee);

    List<Employee> getAllEmployees();

    Optional<Employee> findEmployeeById(Long id);

    Employee update(Employee employee);

    void deleteById(Long id);
}
