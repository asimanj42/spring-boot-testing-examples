package az.company.springboottesting.repository;

import az.company.springboottesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findEmployeeByEmail(String email);

    //index params query
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByIndexParams(String firstName, String lastName);

    //named params query
    @Query("select e from Employee e where e.firstName =:firstName and e.lastName =:lastName")
    Employee findByNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

    //native sql with index params
    @Query(value = "select * from employees e where e.first_name =?1 and e.last_name =?2",nativeQuery = true)
    Employee findByNativeSql(String firstName, String lastName);
}
