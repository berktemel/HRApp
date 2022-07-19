<template>
  <NavigationBar />
  <h2>Unverified Users</h2>
  <div>
    <input class="searchBar" type="text" v-model="search" placeholder="Search">
  </div>
  <table>
    <thead>
    <tr>
      <th>Name</th>
      <th>Last Name</th>
      <th>Email</th>
    </tr>
    </thead>
    <tbody v-for="(user, i) in filteredList" :key="user.id">
      <tr>
        <td>{{ user.name }}</td>
        <td>{{ user.lastName }}</td>
        <td>{{ user.email }}</td>
        <td>
          <button class="editButton" @click="accept(user.id, i)">Verify</button>
        </td>
        <td>
          <button class="deleteButton" @click="decline(user.id, i)">Decline</button>
        </td>
      </tr>
    </tbody>
  </table>
</template>

<script>
import UserService from "../services/UserService";
import NavigationBar from "./NavigationBar.vue"

export default {
  name: "UsersPending",
  components: {
    NavigationBar
  },
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
        if(!allUsers[i].verified) {
          this.users[this.index] = allUsers[i]
          this.index++
        }
      }
    },
    accept(id, i) {
      UserService.verifyUser(id)
      this.users.splice(i, 1)
    },
    decline(id, i) {
      UserService.deleteUser(id)
      this.users.splice(i, 1)
    }
  },
  mounted() {
    this.getUsers()
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
.el-button {
  font-size: 14px;
}
</style>