package az.company.springboottesting.service;

import az.company.springboottesting.exception.AlreadyExistsException;
import az.company.springboottesting.model.Employee;
import az.company.springboottesting.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("asiman")
                .lastName("mammadli")
                .email("asiman@gmail.com")
                .build();
    }

    //JUnit test for save employee method
    @Test
    public void givenEmployee_whenSaveEmployee_thenReturnEmployee() {
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .id(1L)
//                .firstName("asiman")
//                .lastName("mammadli")
//                .email("asiman@gmail.com")
//                .build();
        given(employeeRepository.findEmployeeByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);
        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeService.save(employee);
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //JUnit test for
    @Test
    public void givenExistsEmail_whenSaveEmployee_thenThrowsException() {
        //given - precondition or setup
        given(employeeRepository.findEmployeeByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        //when - action or the behaviour that we are going to test
        assertThrows(AlreadyExistsException.class, () -> {
            employeeService.save(employee);
        });
        //then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    //JUnit test for getAllEmployees method
    @Test
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList() {
        //given - precondition or setup
        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("james")
                .lastName("gosling")
                .email("james@gmail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee2));

        //when - action or the behaviour that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();
        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //JUnit test for findEmployeeById method
    @Test
    public void givenId_whenFindEmployeeById_thenReturnEmployee() {
        //given - precondition or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        //when - action or the behaviour that we are going to test
        Employee findEmployee = employeeService.findEmployeeById(employee.getId()).get();
        //then - verify the output
        assertThat(findEmployee).isNotNull();
    }

    //JUnit test for updateEmployee method
    @Test
    public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        //given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("asimanmammadli@gmail.com");
        //when - action or the behaviour that we are going to test
        Employee updatedEmployee = employeeService.update(employee);
        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("asimanmammadli@gmail.com");
    }

    //JUnit test for deleteById method
    @Test
    public void givenId_whenDeleteEmployee_thenReturnNothing() {
        //given - precondition or setup
        Long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);
        //when - action or the behaviour that we are going to test
        employeeService.deleteById(employeeId);
        //then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}