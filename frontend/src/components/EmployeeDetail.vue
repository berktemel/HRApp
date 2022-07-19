<template>
  <NavigationBar />
  <h2>
    {{ employee.firstName }} {{ employee.lastName }}
  </h2>
  <table>
    <thead>
    <tr>
      <th>Name</th>
      <th>Last Name</th>
      <th>Email</th>
      <th>Department</th>
      <th>Start Date</th>
      <th>Salary</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td>{{ employee.firstName }}</td>
      <td>{{ employee.lastName }}</td>
      <td>{{ employee.email }}</td>
      <td>{{ employee.depName }}</td>
      <td>{{ employee.startDate }}</td>
      <td>{{ employee.salary }}</td>
      <td>
        <router-link :to="{name: 'edit', params: {id: this.$route.params.id}}">
          <button class="editButton">Edit</button>
        </router-link>
      </td>
      <td>
        <button class="deleteButton">Delete</button>
      </td>
    </tr>
    </tbody>
  </table>
  <button style="margin: 20px;" @click="pageRedirect()">Return to Employees page</button>
</template>

<script>
import EmployeeService from "../services/EmployeeService"
import NavigationBar from "./NavigationBar.vue"

export default {
  name: "EmployeeDetail",
  components: {NavigationBar},
  data() {
    return {
      pageId: null,
      employee: [],
    }
  },
  methods: {
    getEmployee() {
      EmployeeService.getEmployee(this.pageId)
          .then(response => {
                this.employee = response.data;
              }
          );
    },
    pageRedirect() {
      this.$router.replace('/');
    },
  },
  mounted() {
    this.pageId = this.$route.params.id;
    this.getEmployee();
  },
}
</script>

<style scoped>
tr {
  border: 1px solid black;
}

th, td {

  border-collapse: collapse;
  border-spacing: 0;
  text-align: center;
}

tr:nth-child(even) {
  background-color: lightgrey;
}

table {
  width: 100%;
  border: 1px solid black;
  border-collapse: collapse;
  border-spacing: 0;
}
</style>