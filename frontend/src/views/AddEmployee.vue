<template>
  <NavigationBar />
  <h1>Add Employee</h1>
  <div v-if="notAdded">
    <ul>
      <li>
        <label><strong>Name:</strong></label>
        <input v-model="employee.firstName" placeholder="Name">
      </li>
      <li>
        <label><strong>Last Name:</strong></label>
        <input v-model="employee.lastName" placeholder="Last Name">
      </li>
      <li>
        <label><strong>Email:</strong></label>
        <input v-model="employee.email" placeholder="Email">
      </li>
      <li>
        <label><strong>Department:</strong></label>
        <input v-model="employee.department.depName" placeholder="Department">
      </li>
      <li>
        <label><strong>Start Date:</strong></label>
        <input v-model="employee.startDate" placeholder="YYYY/MM/DD">
      </li>
      <li>
        <label><strong>Salary:</strong></label>
        <input v-model="employee.salary" placeholder="Salary">
      </li>
    </ul>
    <button @click="addEmployee(); buttonClicked()">Add Employee</button>
    <button @click="pageRedirect()">Cancel</button>
  </div>
  <div v-else class="msg">
    <div>Employee added.</div>
    <button @click="pageRedirect()">Return to Employees page</button>
  </div>
</template>

<script>
import EmployeeService from "../services/EmployeeService"
import UserService from "../services/UserService"
import NavigationBar from "../components/NavigationBar.vue"

export default {
  components: {NavigationBar},
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
      notAdded: true,
    }
  },
  methods: {
    addEmployee() {
      EmployeeService.addEmployee(this.employee);
    },
    buttonClicked() {
      this.notAdded = false;
    },
    pageRedirect() {
      this.$router.replace('/');
    },
  },
}
</script>

<style scoped>
h2 {
  margin-bottom: 50px;
}
label {
  padding: 20px;
}
li {
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