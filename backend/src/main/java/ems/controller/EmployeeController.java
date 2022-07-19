package ems.controller;

import ems.module.Department;
import ems.module.EmployeeDto;
import ems.service.DepartmentService;
import ems.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ems.module.Employee;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        try {
            if(employeeService.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            List<EmployeeDto> employees = employeeService.findAll()
                    .stream().map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") long id) {
        Employee employee = employeeService.findById(id);
        if(employee != null)
            return new ResponseEntity<>(convertToDto(employee), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
        try {
            if(employeeService.emailExists(employeeDto.getEmail())) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Employee employee = convertToEntity(employeeDto);
            addEmployeeToDepartment(employee);
            return new ResponseEntity<>(convertToDto(employee), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> editEmployee(@PathVariable("id") long id, @RequestBody EmployeeDto employeeDto) {
        try {
            Employee employeeExist = employeeService.findById(employeeDto.getId());
            if (employeeExist != null) {
                deleteEmployeeFromDepartment(employeeExist);
                Employee employeeToEdit = convertToEntity(employeeDto);
                employeeToEdit.setId(id);
                addEmployeeToDepartment(employeeToEdit);
                return new ResponseEntity<>(convertToDto(employeeToEdit), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") long id) {
        try {
            Employee employee = employeeService.findById(id);
            deleteEmployeeFromDepartment(employee);
            employeeService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void addEmployeeToDepartment(Employee employee) {
        long empDepId = employee.getDepartment().getDepartmentId();
        if(employee.getDepartment().getDepName() != null) {
            empDepId = departmentService.getIdByName(employee.getDepartment().getDepName());
        }
        Department empsDep = departmentService.findById(empDepId);
        employee.setDepartment(empsDep);
        empsDep.addEmployee(employee);
        departmentService.save(empsDep);
    }

    public void deleteEmployeeFromDepartment(Employee employee) {
        long empDepId = employee.getDepartment().getDepartmentId();
        departmentService.findById(empDepId).deleteEmployee(employee);
    }

    private EmployeeDto convertToDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    private Employee convertToEntity(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }
}
