package ems.module;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.io.Serializable;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;
    

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "departmentId", nullable = false)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JsonBackReference
    private Department department;
    
    @Column(name = "startDate")
    private String startDate;
    
    @Column(name = "salary")
    private int salary;

    public Employee() {
    }

    public Employee(long id, String firstName, String lastName, String email, Department department, String startDate, int salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.startDate = startDate;
        this.salary = salary;
    }

    public Employee(Employee employee) {
        this.id = employee.id;
        this.firstName = employee.firstName;
        this.lastName = employee.lastName;
        this.email = employee.email;
        this.department = employee.department;
        this.startDate = employee.startDate;
        this.salary = employee.salary;
    }

    public void copy(Employee employee) {
        this.id = employee.id;
        this.firstName = employee.firstName;
        this.lastName = employee.lastName;
        this.email = employee.email;
        this.department = employee.department;
        this.startDate = employee.startDate;
        this.salary = employee.salary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public int getSalary() {
    	return salary;
    }
    
    public void setSalary(int salary) {
    	this.salary = salary;
    }

    public String getDepartmentName() {
        return department.getDepName();
    }
}
