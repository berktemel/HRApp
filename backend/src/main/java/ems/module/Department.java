package ems.module;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
@Data
@Entity
@Table(name = "departments")
public class Department implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long departmentId;

	@Column(name = "depName")
	private String depName;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Employee> employees = new ArrayList<>();

	public Department() {}

	public Department(long departmentId, String depName, ArrayList<Employee> employees) {
		this.departmentId = departmentId;
		this.depName = depName;
		this.employees = employees;
	}

	public Department(Department department) {
		this.departmentId = department.departmentId;
		this.depName = department.depName;
		this.employees = department.employees;
	}

	public void copy(Department department) {
		this.departmentId = department.departmentId;
		this.depName = department.depName;
		this.employees = department.employees;
	}
	public long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public void addEmployee(Employee employee) {
		employees.add(employee);
	}

	public void deleteEmployee(Employee employee) {
		employees.remove(employee);
	}
	@Override
	public String toString() {
		return "{\n\tid: " + this.departmentId + "\n\tdepName: " + this.depName + "\n}";
	}
}
