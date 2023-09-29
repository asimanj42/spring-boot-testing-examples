package az.company.springboottesting.service;

import az.company.springboottesting.exception.AlreadyExistsException;
import az.company.springboottesting.model.Employee;
import az.company.springboottesting.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee save(Employee employee) {
        Optional<Employee> savedEmployee = employeeRepository.findEmployeeByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) {
            throw new AlreadyExistsException("Employee already exists email " + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee update(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
}
