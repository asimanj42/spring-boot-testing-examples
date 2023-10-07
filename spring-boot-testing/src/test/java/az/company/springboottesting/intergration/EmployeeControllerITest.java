package az.company.springboottesting.intergration;

import az.company.springboottesting.entity.Employee;
import az.company.springboottesting.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
    }

    @Test
    public void givenEmployee_whenSave_thenReturnSavedEmployee() throws Exception {

        // given - precondition or setup
        Employee employee = Employee.builder().
                firstName("asiman")
                .lastName("mammadli")
                .email("asiman@gmail.com")
                .build();

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


    @Test
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("asiman")
                .lastName("mammadli")
                .email("asiman@gmail.com")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("muammar")
                .lastName("gaddafi")
                .email("gaddafi@gmail.com")
                .build();
        List<Employee> employeeList = new ArrayList<>(List.of(employee, employee2));
        employeeRepository.saveAll(employeeList);

        //when - action or the behaviour that we are going to test
        ResultActions result = mockMvc.perform(get("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeList))
        );
        //then - verify the output

        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(employeeList.size())));
    }

    @Test
    public void givenEmployee_whenFindEmployeeById_thenEmployee() throws Exception {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("asiman")
                .lastName("mammadli")
                .email("asiman@gmail.com")
                .build();
        employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        ResultActions result = mockMvc.perform(get("/api/v1/employees/{id}", employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );
        //then - verify the output
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {
        // given - precondition or setup
        Employee saved = Employee.builder()
                .firstName("asiman")
                .lastName("mammadli")
                .email("asiman@gmail.com")
                .build();
        employeeRepository.save(saved);
        Employee updated = Employee.builder()
                .firstName("fidel")
                .lastName("castro")
                .email("fidel@gmail.com")
                .build();
        //when - action or the behaviour that we are going to test
        ResultActions result = mockMvc.perform(put("/api/v1/employees/{id}", saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated))
        );
        //then - verify the output
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updated.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updated.getLastName())))
                .andExpect(jsonPath("$.email", is(updated.getEmail())));
    }

    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenEmployeeDeletedSuccessfully() throws Exception {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("asiman")
                .lastName("mammadli")
                .email("asiman@gmail.com")
                .build();
        employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        ResultActions result = mockMvc.perform(delete("/api/v1/employees/{id}", employee.getId()));
        //then - verify the output
        result.andExpect(status().isOk())
                .andDo(print());
    }
}
