package ems.service;

import ems.module.Department;

import java.util.List;


public interface DepartmentService {

    List<Department> findAll();

    Department findById(long id);

    void save(Department department);

    void deleteById(long id);

    boolean isEmpty();

    long getIdByName(String depName);

    Department update(Long id, Department department);


}
