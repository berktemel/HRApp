<template>
  <NavigationBar />
  <h2>Add Department</h2>
  <div v-if="notAdded">
    <ul>
      <li>
        <label><strong>Department Name:</strong></label>
        <input v-model="department.depName" placeholder="Department Name">
      </li>
    </ul>
    <button @click="addDepartment(); buttonClicked()">Add Department</button>
    <button @click="pageRedirect()">Cancel</button>
  </div>
  <div v-else class="msg">
    <div>Department added.</div>
    <button @click="pageRedirect()">Return to Departments page</button>
  </div>
</template>

<script>
import DepartmentService from "../services/DepartmentService"
import UserService from "../services/UserService"
import NavigationBar from "../components/NavigationBar.vue"

export default {
  components: {NavigationBar},
  data() {
    return {
      department: {
        departmentId: null,
        depName: "",
        employees: []
      },
      notAdded: true,
    }
  },
  methods: {
    addDepartment() {
      DepartmentService.addDepartment(this.department);
    },
    buttonClicked() {
      this.notAdded = false;
    },
    pageRedirect() {
      this.$router.replace('/departments');
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

button {
  margin: 20px;
  padding: 13px;
}

ul {
  list-style-type: none;
}

.msg {
  font-size: 20px;
  padding: 40px;
}
</style>