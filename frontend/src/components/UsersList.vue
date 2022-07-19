<template>
  <NavigationBar />
  <h2>User List</h2>
  <div>
    <input class="searchBar" type="text" v-model="search" placeholder="Search">
  </div>
  <table>
    <thead>
    <tr>
      <th>Name</th>
      <th>Last Name</th>
      <th>Email</th>
      <th>Role</th>
    </tr>
    </thead>
    <tbody v-for="(user, index) in filteredList" :key="user.id">
    <tr>
      <td>{{ user.name }}</td>
      <td>{{ user.lastName }}</td>
      <td>{{ user.email }}</td>
      <td>{{ user.role }}</td>
      <td>
        <button class="deleteButton" @click="deleteUser(user.id, index)">Delete</button>
      </td>
    </tr>
    </tbody>
  </table>
</template>

<script>
import EmployeeService from "../services/EmployeeService";
import UserService from "../services/UserService";
import NavigationBar from "./NavigationBar.vue"

export default {
  name: "UsersList",
  components: {NavigationBar},
  data() {
    return {
      users: [],
      search: "",
      index: 0,
    }
  },
  methods: {
    async getUsers() {
      let allUsers = []
      await UserService.getUsers()
          .then(response => {
            allUsers = response.data
          })

      for(let i = 0; i < allUsers.length ; i++) {
        if(allUsers[i].verified) {
          this.users[this.index] = allUsers[i]
          this.index++
        }
      }
    },
    redirectPage(path) {
      this.$router.replace(path);
    },
    deleteUser(id, index) {
      UserService.deleteUser(id)
      this.users.splice(index, 1)
    },
  },
  mounted() {
    this.getUsers();
  },
  computed: {
    filteredList() {
      return this.users.filter((user) => {
            return user.name.toLowerCase().includes(this.search.toLowerCase())
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
  width: 80%;
  border: 1px solid black;
  border-collapse: collapse;
  border-spacing: 0;
}

.addEmpButton {
  margin: 20px;
  float: left;
  padding: 15px;
  font-size: 20px;
}
</style>