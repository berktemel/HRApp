import http from "../httpSource";

class EmployeeService {
    getEmployees() {
        return http.get("/employees");
    }

    getEmployee(id) {
        return http.get(`/employees/${id}`)
    }

    addEmployee(employee) {
        return http.post("/employees", employee)
    }

    editEmployee(id, employee) {
        return http.put(`/employees/${id}`, employee)
    }

    deleteEmployee(id) {
        return http.delete(`/employees/${id}`)
    }


}

export default new EmployeeService();