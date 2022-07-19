package ems.module;

import lombok.Data;

@Data
public class EmployeeDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Department department;
    private String startDate;
    private int salary;

    public String getDepName() {
        return department.getDepName();
    }
}
