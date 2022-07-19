<template>
  <h1>Signup</h1>
  <div v-if="!signedUp">
    <ul>
      <li>
        <form name="firstName">
          <label><strong>Name:</strong></label>
          <input name="fName" v-model="user.name" placeholder="Name">
        </form>
      </li>
      <li>
        <form name="lastName">
          <label><strong>Last Name:</strong></label>
          <input name="lName" v-model="user.lastName" placeholder="Last Name">
        </form>
      </li>
      <li>
        <form name="email">
          <label><strong>Email:</strong></label>
          <input name="mail" v-model="user.email" placeholder="mail@example.com">
        </form>
      </li>
      <li>
        <form name="password">
          <label><strong>Password:</strong></label>
          <input name="pw" type="password" v-model="user.password" placeholder="******">
        </form>
      </li>
    </ul>
    <button @click="signup()">Signup</button>
    <button @click="redirectPage()">Cancel</button>
  </div>
  <div v-else>
    <div>Signup successful.</div>
    <button @click="redirectPage()">Login Page</button>
  </div>
</template>

<script>
function checkEmpty(formName, inputName) {
  var x = document.forms[formName][inputName].value;
  if (x == "") {
    return true;
  }
  return false;
}

import UserService from "../services/UserService"

export default {
  data() {
    return {
      user: {
        name: "",
        lastName: "",
        email: "",
        password: ""
      },
      filled: false,
      signedUp: false
    }
  },
  methods: {
    signup() {
      if (!(checkEmpty("firstName", "fName") || checkEmpty("lastName", "lName") ||
          checkEmpty("email", "mail") || checkEmpty("password", "pw"))) {
        this.filled = true;
        this.signedUp = true;
        UserService.addUser(this.user);
      }
    },
    redirectPage() {
      this.$router.replace('/login');
    }
  }
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
button {
  margin: 20px;
  padding: 13px;
  width: 100px;
}
</style>