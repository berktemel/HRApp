package ems.service;

import ems.module.Employee;
import ems.repository.EmployeeRepository;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> findByFirstName(String firstName) {
        return employeeRepository.findByFirstName(firstName);
    }

    @Override
    public Employee findById(long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        Employee emp;
        if(employee.isPresent()) {
            emp = employee.get();
        } else {
            throw new RuntimeException("Employee with id " + id + "doesn't exist.");
        }
        return emp;
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteById(long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Employee with id " + id + "doesn't exist.");
        }
    }

    @Override
    public boolean isEmpty() {
        return employeeRepository.count() == 0;
    }

    @Override
    public Employee update(Long id, Employee employee) {
        Employee originalEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Department doesn't exist"));
        originalEmployee.copy(employee);
        return employeeRepository.save(originalEmployee);
    }

    @Override
    public boolean emailExists(String email) {
        return employeeRepository.findByEmail(email) != null;
    }
}
