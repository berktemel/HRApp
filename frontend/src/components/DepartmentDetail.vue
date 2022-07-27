<template>
  <NavigationBar />
  <h2>
    <span>{{ this.department.depName }}</span>
  </h2>
  <div>
    <input class="searchBar" type="text" placeholder="Search">
  </div>
  <table>
    <thead>
    <tr>
      <th>Name</th>
      <th>Last Name</th>
      <th>Email</th>
    </tr>
    </thead>
    <tbody v-for="employee in department.employees" :key="employee.id">
    <tr>
      <td>{{ employee.firstName }}</td>
      <td>{{ employee.lastName }}</td>
      <td>{{ employee.email }}</td>
      <td>
        <router-link :to="{name: 'employee', params: {id: employee.id}}">
          <button class="viewButton">View</button>
        </router-link>
      </td>
      <td>
        <router-link :to="{name: 'edit', params: {id: employee.id}}">
          <button class="editButton">Edit</button>
        </router-link>
      </td>
      <td>
        <button class="deleteButton">Delete</button>
      </td>
    </tr>
    </tbody>
  </table>
  <button class="returnButton" @click="pageRedirect()">Return to Department list</button>
</template>

<script>
import DepartmentService from "../services/DepartmentService"
import NavigationBar from "./NavigationBar.vue";

export default {
  name: "DepartmentDetail",
  components: {NavigationBar},
  data() {
    return {
      pageId: null,
      department: [],
      employees: [],
    }
  },
  methods: {
    getDepartment() {
      DepartmentService.getDepartment(this.pageId)
          .then(response => {
                this.department = response.data;
              }
          );
    },
    pageRedirect() {
      this.$router.replace('/departments');
    },
  },
  mounted() {
    this.pageId = this.$route.params.id;
    this.getDepartment();
  },
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
  background-color: lightgrey;
}

table {
  width: 80%;
  border: 1px solid black;
  border-collapse: collapse;
  border-spacing: 0;
}

.returnButton {
  margin: 20px;
  padding: 15px;
}
</style>
