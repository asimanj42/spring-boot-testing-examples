package az.company.springboottesting.controller;

import az.company.springboottesting.entity.Employee;
import az.company.springboottesting.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    Employee employee;

    @BeforeEach
    public void setUp() {
        employee = Employee.builder().
                firstName("asiman")
                .lastName("mammadli")
                .email("asiman@gmail.com")
                .build();
    }

    //JUnit test for save employee rest api
    @Test
    public void givenEmployee_whenSave_thenReturnSavedEmployee() throws Exception {

        //given - precondition or setup4
//        Employee employee = Employee.builder().
//                firstName("asiman")
//                .lastName("mammadli")
//                .email("asiman@gmail.com")
//                .build();
        given(employeeService.save(any(Employee.class))).willAnswer(
                invocation -> invocation.getArgument(0)
        );
        //when - action or the behaviour that we are going to test
        ResultActions result = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));
        //then - verify the output
        result.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    //JUnit test for get all employees
    @Test
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
        //given - precondition or setup
        List<Employee> employeeList = new ArrayList<>();
//        employeeList.add(Employee.builder()
//                .firstName("asiman")
//                .lastName("mammadli")
//                .email("asiman@gmail.com")
//                .build());
        employeeList.add(Employee.builder()
                .firstName("james")
                .lastName("gosling")
                .email("james@gmail.com")
                .build());
        given(employeeService.getAllEmployees()).willReturn(employeeList);
        //when - action or the behaviour that we are going to test
        ResultActions result = mockMvc.perform(get("/api/v1/employees"));

        //then - verify the output
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(employeeList.size())));
    }

    //JUnit test for find employee by id
    @Test
    public void givenEmployee_whenFindEmployeeById_thenEmployee() throws Exception {
        //given - precondition or setup
        Long employeeId = 1L;
//        Employee employee = Employee.builder().
//                firstName("asiman")
//                .lastName("mammadli")
//                .email("asiman@gmail.com")
//                .build();
        given(employeeService.findEmployeeById(employeeId)).willReturn(Optional.of(employee));
        //when - action or the behaviour that we are going to test
        ResultActions result = mockMvc.perform(get("/api/v1/employees/{id}", employeeId));
        //then - verify the output
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    //JUnit test for update employee
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {
        //given - precondition or setup
        Long employeeId = 1L;
//        Employee savedEmployee = Employee.builder()
//                .firstName("asiman")
//                .lastName("mammadli")
//                .email("asiman@gmail.com")
//                .build();
        Employee updatedEmployee = Employee.builder()
                .firstName("karl")
                .lastName("marx")
                .email("karlmarx@gmail.com")
                .build();
        given(employeeService.findEmployeeById(employeeId)).willReturn(Optional.of(employee));
        given(employeeService.update(any(Employee.class))).willAnswer(
                invocation -> invocation.getArgument(0)
        );
        //when - action or the behaviour that we are going to test
        ResultActions result = mockMvc.perform(put("/api/v1/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
    }

    //JUnit test for delete employee
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenEmployeeDeletedSuccessfully() throws Exception {
        //given - precondition or setup
        Long employeeId = 1L;
        willDoNothing().given(employeeService).deleteById(employeeId);
        //when - action or the behaviour that we are going to test
        ResultActions result = mockMvc.perform(delete("/api/v1/employees/{id}", employeeId));
        //then - verify the output
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", is("Employee deleted successfully")));
    }
}