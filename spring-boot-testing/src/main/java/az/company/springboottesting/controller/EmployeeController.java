package az.company.springboottesting.controller;

import az.company.springboottesting.entity.Employee;
import az.company.springboottesting.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee save(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findAll() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        return employeeService.findEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.findEmployeeById(id)
                .map(updatedEmployee -> {
                    updatedEmployee.setFirstName(employee.getFirstName());
                    updatedEmployee.setLastName(employee.getLastName());
                    updatedEmployee.setEmail(employee.getEmail());
                    return ResponseEntity.ok(employeeService.update(updatedEmployee));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
