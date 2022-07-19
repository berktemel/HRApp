package ems.module;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DepartmentDto {
    private long departmentId;
    private String depName;
    private List<Employee> employees = new ArrayList<>();
}
