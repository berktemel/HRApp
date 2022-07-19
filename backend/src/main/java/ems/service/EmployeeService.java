package ems.service;

import ems.module.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    List<Employee> findAll();

    List<Employee> findByFirstName(String firstName);

    Employee findById(long id);

    void save(Employee employee);
    void deleteById(long id);

    boolean isEmpty();

    Employee update(Long id, Employee employee);
    boolean emailExists(String email);
}
