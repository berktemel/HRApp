package ems.controller;

import java.util.List;
import java.util.stream.Collectors;

import ems.module.DepartmentDto;
import ems.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ems.module.Department;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class DepartmentController {
	@Autowired
    private DepartmentService departmentService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentDto>> getDepartments() {
        try {
            if(departmentService.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            List<DepartmentDto> departments = departmentService.findAll()
                    .stream().map(this::convertToDto)
                            .collect(Collectors.toList());
            return new ResponseEntity<>(departments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("id") long id) {
        Department department = departmentService.findById(id);
        if(department != null)
            return new ResponseEntity<>(convertToDto(department), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/departments")
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody DepartmentDto departmentDto) {
        try {
            Department department = convertToEntity(departmentDto);
            departmentService.save(department);
            DepartmentDto response = convertToDto(department);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<DepartmentDto> editDepartment(@PathVariable("id") long id, @RequestBody DepartmentDto departmentDto) {
        try {
            Department departmentToEdit = convertToEntity(departmentDto);
            departmentToEdit.setDepartmentId(id);
            departmentService.save(departmentToEdit);
            return new ResponseEntity<>(convertToDto(departmentToEdit), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<HttpStatus> deleteDepartment(@PathVariable("id") long id) {
        try {
            departmentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private DepartmentDto convertToDto(Department department) {
        return modelMapper.map(department, DepartmentDto.class);
    }

    private Department convertToEntity(DepartmentDto departmentDto) {
        return modelMapper.map(departmentDto, Department.class);
    }
	
}
