<template>
  <NavigationBar />
  <h2>
    Employee List
  </h2>
  <div>
    <input class="searchBar" type="text" v-model="search" placeholder="Search">
  </div>
  <table>
    <thead>
      <tr>
        <th>Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>Department</th>
      </tr>
    </thead>
    <tbody v-for="(employee, index) in filteredList" :key="employee.id">
      <tr>
        <td>{{ employee.firstName }}</td>
        <td>{{ employee.lastName }}</td>
        <td>{{ employee.email }}</td>
        <td>{{ employee.depName }}</td>
        <td>
          <router-link :to="{name: 'employee', params: {id: employee.id}}">
            <button class="viewButton">View</button>
          </router-link>
        </td>
        <td>
          <router-link :to="{name: 'edit', params: {id: employee.id}}">
            <button class="editButton">Edit </button>
          </router-link>
        </td>
        <td>
          <button class="deleteButton" @click="deleteEmployee(employee.id, index)">Delete</button>
        </td>
      </tr>
    </tbody>
  </table>
  <button @click="redirectPage('/addemployee')" class="addButton">Add Employee</button>
</template>

<script>
import EmployeeService from "../services/EmployeeService"
import NavigationBar from "./NavigationBar.vue"

export default {
  name: "EmployeeList",
  components: {NavigationBar},
  data() {
    return {
      employees: [],
      search: "",
    }
  },
  methods: {
    getEmployees() {
      EmployeeService.getEmployees()
          .then(response => {
                this.employees = response.data;
              }
          );
    },
    redirectPage(path) {
      this.$router.replace(path);
    },
    deleteEmployee(id, index) {
      EmployeeService.deleteEmployee(id);
      this.employees.splice(index, 1)
    },
  },
  mounted() {
    const notRefreshed = localStorage.getItem("refresh")
    if(JSON.parse(notRefreshed)) {
      localStorage.setItem("refresh", false)
      this.$router.go()
    }
    this.getEmployees();
  },
  computed: {
    filteredList() {
      return this.employees.filter((employee) => {
            return employee.firstName.toLowerCase().includes(this.search.toLowerCase())
          }
      )
    },
  }
}
</script>

<style scoped>
.searchBar {
  padding: 10px;
  width: 300px;
  font-size: 17px;
  display: block;
  margin: 20px auto;
}

tr {
  border: 1px solid black;
}

th, td {

  border-collapse: collapse;
  border-spacing: 0;
  text-align: center;
}

tbody:nth-child(even) {
  background-color: lightgray;
}

table {
  width: 80%;
  border: 1px solid black;
  border-collapse: collapse;
  border-spacing: 0;
}

.addButton {
  margin: 20px;
  float: left;
  padding: 15px;
  font-size: 20px;
}
</style>