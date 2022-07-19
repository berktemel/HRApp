<template>
  <NavigationBar />
  <h2>
    Department List
  </h2>
  <div>
    <input class="searchBar" v-model="search" type="text" placeholder="Search">
  </div>
  <table>
    <thead>
    <tr>
      <th>Department Name</th>
    </tr>
    </thead>
    <tbody v-for="(department, index) in filteredList" :key="department.departmentId">
    <tr>
      <td>{{ department.depName }}</td>
      <td>
        <router-link :to="{name: 'department', params: {id: department.departmentId}}">
          <button class="viewButton">View</button>
        </router-link>
      </td>
      <td>
        <button class="deleteButton" @click="deleteDepartment(department.departmentId, index)">Delete</button>
      </td>
    </tr>
    </tbody>
  </table>
  <div class="buttonDiv">
    <button class="addButton" @click="redirectPage('/adddepartment')">Add Department</button>
  </div>
</template>

<script>
import DepartmentService from "../services/DepartmentService"
import NavigationBar from "./NavigationBar.vue";

export default {
  name: "DepartmentList",
  components: {NavigationBar},
  data() {
    return {
      departments: [],
      search: "",
    }
  },
  methods: {
    getDepartments() {
      DepartmentService.getDepartments()
          .then(response => {
                this.departments = response.data;
              }
          );
    },
    redirectPage(path) {
      this.$router.replace(path);
    },
    deleteDepartment(id, index) {
      DepartmentService.deleteDepartment(id);
      this.departments.splice(index, 1)
    },
  },
  mounted() {
    this.getDepartments();
  },
  computed: {
    filteredList() {
      return this.departments.filter((department) => {
            return department.depName.toLowerCase().includes(this.search.toLowerCase())
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
  background-color: lightgrey;
}

table {
  width: 40%;
  border: 1px solid black;
  border-collapse: collapse;
  border-spacing: 0;
  margin: 0 auto;
}

.addButton {
  margin: 20px;
  float: left;
  padding: 15px;
  font-size: 15px;
  display: block;
}

.buttonDiv {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>