package ems.module;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class EmployeeDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnoreProperties(value = "employees")
    private Department department;
    private String startDate;
    private int salary;
}
