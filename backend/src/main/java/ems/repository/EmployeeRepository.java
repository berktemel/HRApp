package ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ems.module.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByFirstName(String firstName);
    Employee findByEmail(String email);
}
