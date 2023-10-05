package az.company.springboottesting.repository;

import az.company.springboottesting.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    Employee employee;

    @BeforeEach
    public void setUp() {
        employee = Employee.builder().
                firstName("asiman")
                .lastName("mammadli")
                .email("asiman@gmail.com")
                .build();
    }

    //JUnit test for save employee operation
    @Test
    public void givenEmployee_whenSave_thenReturnSavedEmployee() {

        //given - precondition or setup
//        Employee employee = Employee.builder().
//                firstName("asiman")
//                .lastName("mammadli")
//                .email("asiman@gmail.com")
//                .build();
        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getFirstName()).isEqualTo("asiman");
    }

    //JUnit test for find all employee
    @Test
    public void givenEmployeeList_whenFindAll_thenReturnEmployeesList() {
        //given - precondition or setup
//        Employee employee =Employee.builder().
//                firstName("asiman")
//                .lastName("mammadli")
//                .email("asiman@gmail.com")
//                .build();
        Employee employee2 = Employee.builder()
                .firstName("james")
                .lastName("gosling")
                .email("james@gmail.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        //when - action or the behaviour that we are going to test
        List<Employee> employeeList = employeeRepository.findAll();
        //then - verify the output
        assertThat(employeeList.size()).isEqualTo(2);
        assertThat(employeeList).isNotNull();
    }

    //JUnit test for get employee by id operation
    @Test
    public void givenEmployee_whenFindById_thenReturnEmployeeDatabaseObject() {
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("asiman")
//                .lastName("mammadli")
//                .email("asiman@gmail.com")
//                .build();
        employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        Employee employeeDb = employeeRepository.findById(employee.getId()).get();
        //then - verify the output
        assertThat(employeeDb).isNotNull();
    }

    //JUnit test for findEmployeeByEmail operation
    @Test
    public void givenEmployee_whenFindEmployeeByEmail_thenReturnEmployeeDatabaseObject() {
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("asiman")
//                .lastName("mammadli")
//                .email("asiman@gmail.com")
//                .build();
        employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        Employee employeeDb = employeeRepository.findEmployeeByEmail(employee.getEmail()).get();
        //then - verify the output

        assertThat(employeeDb).isNotNull();
//        assertThat(employeeDb.getId()).isBetween(0L, 6L);
    }

    //JUnit test for delete employee operation
    @Test
    public void givenEmployee_whenDeleteEmployee_thenEmployeeShouldNotExist() {
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("asiman")
//                .lastName("mammadli")
//                .email("asiman@gmail.com")
//                .build();
        employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        employeeRepository.delete(employee);
        Optional<Employee> employee2 = employeeRepository.findById(employee.getId());
        //then - verify the output
        assertThat(employee2).isEmpty();
    }

    //JUnit test for finding employee by index params
    @Test
    public void givenEmployee_whenFindEmployeeByIndexParams_thenEmployeeShouldExist() {
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("asiman")
//                .lastName("mammadli")
//                .email("asiman@gmail.com")
//                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are going to test
        Employee wantedEmployee = employeeRepository.findByIndexParams("asiman", "mammadli");

        //then - verify the output
        assertThat(wantedEmployee).isNotNull();
    }


    //JUnit test for
    @Test
//    @DisplayName(value = "test name")
    public void givenEmployee_whenFindEmployeeByNamedParams_thenEmployeeShouldExist() {
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("asiman")
//                .lastName("mammadli")
//                .email("asiman@gmail.com")
//                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are going to test
        Employee findEmployee = employeeRepository.findByNamedParams("asiman", "mammadli");
        //then - verify the output
        assertThat(findEmployee).isNotNull();
    }

    @Test
    public void givenEmployee_whenFindEmployeeByNativeQueryNamedParams_thenEmployeeShouldExist() {
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("asiman")
//                .lastName("mammadli")
//                .email("asiman@gmail.com")
//                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are going to test
        Employee findEmployee = employeeRepository.findByNativeSql("asiman", "mammadli");
        //then - verify the output
        assertThat(findEmployee).isNotNull();
    }
}
