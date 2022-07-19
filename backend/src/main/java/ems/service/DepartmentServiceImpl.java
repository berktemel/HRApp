package ems.service;

import ems.module.Department;
import ems.repository.DepartmentRepository;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(long id) {
        Optional<Department> department = departmentRepository.findById(id);
        Department dep;
        if(department.isPresent()) {
            dep = department.get();
        } else {
            throw new RuntimeException("Department with id " + id + "doesn't exist.");
        }
        return dep;
    }

    @Override
    public void save(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public void deleteById(long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent()) {
            departmentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Department with id " + id + "doesn't exist.");
        }
    }

    @Override
    public boolean isEmpty() {
        return departmentRepository.count() == 0;
    }

    @Override
    public long getIdByName(String depName) {
        long id = -1;
        List<Department> departments = findAll();
        for (Department department : departments) {
            if (department.getDepName().equals(depName)) {
                id = department.getDepartmentId();
                break;
            }
        }
        return id;
    }

    @Override
    public Department update(Long id, Department department) {
        Department originalDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Department doesn't exist"));
        originalDepartment.copy(department);
        return departmentRepository.save(originalDepartment);
    }
}
