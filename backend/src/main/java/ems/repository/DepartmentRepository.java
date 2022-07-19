package ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ems.module.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
