<template>
  <h2>Reset Password</h2>
  <div v-if="!changed">
    <ul>
      <li>
        <form name="password">
          <label><strong>Password:</strong></label>
          <input name="pw" type="password" v-model="pwFirst" placeholder="******">
        </form>
      </li>
      <li>
        <form name="password2">
          <label><strong>Password (Again):</strong></label>
          <input name="pw2" type="password" v-model="pwAgain" placeholder="******">
        </form>
      </li>
    </ul>
    <div v-if="!pwsSame">Passwords should be same.</div>
    <button @click="submit()">Submit</button>
    <button @click="redirectPage()">Cancel</button>
  </div>
  <div v-else>
    <div>Password changed successfully.</div>
    <button @click="redirectPage()">Login Page</button>
  </div>
</template>

<script>
import UserService from "../services/UserService"

export default {
  name: "ResetPassword",
  data() {
    return {
      changed: false,
      user: [],
      pwFirst: "",
      pwAgain: "",
      pwsSame: true,
    }
  },
  methods: {
    redirectPage() {
      this.$router.replace('/login')
    },
    getUser() {
      UserService.getUserByResetPwToken(window.location.pathname.split('/').pop())
          .then(response => {
        this.user = response.data
      })
    },
    submit() {
      if(this.checkPasswords()) {
        this.user.password = this.pwFirst
        this.changed = true
        UserService.editUser(this.user)
        UserService.expireToken(this.user.id)
        localStorage.removeItem("resetKey")
      }
    },
    checkPasswords() {
      return this.pwsSame = (this.pwFirst === this.pwAgain)
    }
  },
  mounted() {
    this.getUser()
  },
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
