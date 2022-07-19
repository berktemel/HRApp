<template>
  <router-view />
</template>

<script>
import UserService from "./services/UserService";
export default {
  data() {
    return {
    }
  },
  methods: {
    redirectPage(path) {
      this.$router.replace(path);
    },
    isTokenNullOrInvalid() {
      return localStorage.getItem("token") == null
          || !UserService.checkToken(localStorage.getItem("userEmail"))
    },
    isResetKeyNull() {
      return localStorage.getItem("resetKey") == null
    },
    isUserVerified() {
      return UserService.getUser(localStorage.getItem("userEmail")).verified
    }
  },
  mounted() {
    if(this.isTokenNullOrInvalid() && this.isResetKeyNull() && !this.isUserVerified()) {
      this.redirectPage('/login')
    }
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.viewButton{
  border-radius: 10px;
  cursor: pointer;
  background-color: cornflowerblue;

  border: none;
}
.viewButton:hover {
  background-color: royalblue;
}
.editButton{
  border-radius: 10px;
  cursor: pointer;
  background-color: greenyellow;

  border: none;
}
.editButton:hover {
  background-color: green;
}
.deleteButton{
  border-radius: 10px;
  cursor: pointer;
  background-color: red;

  border: none;
}
.deleteButton:hover {
  background-color: darkred;
}
</style>
