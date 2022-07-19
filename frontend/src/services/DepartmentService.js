import http from "../httpSource";

class DepartmentService {
    getDepartments() {
        return http.get("/departments");
    }

    getDepartment(id) {
        return http.get(`/departments/${id}`)
    }

    addDepartment(department) {
        return http.post("/departments", department)
    }

    editDepartment(id, department) {
        return http.put(`/departments/${id}`, department)
    }

    deleteDepartment(id) {
        return http.delete(`/departments/${id}`)
    }
}

export default new DepartmentService();