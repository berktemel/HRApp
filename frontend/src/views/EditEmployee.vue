<template>
  <NavigationBar />
  <div v-if="notEdited">
    <ul>
      <li>
        <form name="firstName">
          <label><strong>Name:</strong></label>
          <input name="fName" v-model="employee.firstName" :placeholder="employeeData.firstName">
        </form>
      </li>
      <li>
        <form name="lastName">
          <label><strong>Last Name:</strong></label>
          <input name="lName" v-model="employee.lastName" :placeholder="employeeData.lastName">
        </form>
      </li>
      <li>
        <form name="email">
          <label><strong>Email:</strong></label>
          <input name="mail" v-model="employee.email" :placeholder="employeeData.email">
        </form>
      </li>
      <li>
        <form name="department">
          <label><strong>Department:</strong></label>
          <input name="dep" v-model="employee.department.depName" :placeholder="employeeData.department.depName">
        </form>
      </li>
      <li>
        <form name="startDate">
          <label><strong>Start Date:</strong></label>
          <input name="sDate" v-model="employee.startDate" :placeholder="employeeData.startDate">
        </form>
      </li>
      <li>
        <form name="salary">
          <label><strong>Salary:</strong></label>
          <input name="sal" v-model="employee.salary" :placeholder="employeeData.salary">
        </form>
      </li>
    </ul>
    <button @click="buttonClicked()">Edit Employee</button>
    <button @click="pageRedirect()">Cancel</button>
  </div>
  <div v-else class="msg">
    <div>Employee edited.</div>
    <button @click="pageRedirect()">Return to Employees page</button>
  </div>
</template>

<script>
import EmployeeService from "../services/EmployeeService"
import NavigationBar from "../components/NavigationBar.vue"

function checkEmpty(formName, inputName) {
  var x = document.forms[formName][inputName].value;
  return x == "";

}

export default {
  components: { NavigationBar},
  data() {
    return {
      employee: {
        firstName: "",
        lastName: "",
        email: "",
        department: {
          depName: "",
        },
        startDate: "",
        salary: null
      },
      employeeData: {
        firstName: "",
        lastName: "",
        email: "",
        department: {
          depName: "",
        },
        startDate: "",
        salary: null
      },
      notEdited: true,
      id: null,
      nameEmpty: true,
      lastNameEmpty: true,
      emailEmpty: true,
      departmentEmpty: true,
      startDateEmpty: true,
      salaryEmpty: true,
    }
  },
  methods: {
    getEmployee() {
      EmployeeService.getEmployee(this.id)
          .then(response => {
                this.employeeData = response.data;
              }
          )
    },
    editEmployee() {
      EmployeeService.editEmployee(this.id, this.employee);
    },
    buttonClicked() {
      this.notEdited = false;
      this.nameEmpty = checkEmpty("firstName", "fName");
      if (this.nameEmpty) {
        this.employee.firstName = this.employeeData.firstName;
      }

      this.lastNameEmpty = checkEmpty("lastName", "lName");
      if (this.lastNameEmpty) {
        this.employee.lastName = this.employeeData.lastName;
      }

      this.emailEmpty = checkEmpty("email", "mail");
      if (this.emailEmpty) {
        this.employee.email = this.employeeData.email;
      }

      this.departmentEmpty = checkEmpty("department", "dep");
      if (this.departmentEmpty) {
        this.employee.department.depName = this.employeeData.depName;
      }

      this.startDateEmpty = checkEmpty("startDate", "sDate");
      if (this.startDateEmpty) {
        this.employee.startDate = this.employeeData.startDate;
      }

      this.salaryEmpty = checkEmpty("salary", "sal");
      if (this.salaryEmpty) {
        this.employee.salary = this.employeeData.salary;
      }
      this.editEmployee();
    },
    pageRedirect() {
      this.$router.replace('/');
    },
  },
  mounted() {
    this.id = this.$route.params.id;
    this.getEmployee();
  },
}
</script>

<style scoped>
label {
  padding: 20px;
}
form {
  margin: 10px auto;
  width: 27%;
  display: flex;
}

input {
  height: 25px;
  margin-left: auto;
  margin-top: auto;
  margin-bottom: auto;
}

ul {
  list-style-type: none;
}

.msg {
  font-size: 20px;
  padding: 40px;
}

button {
  margin: 20px;
  padding: 10px;
}
</style>