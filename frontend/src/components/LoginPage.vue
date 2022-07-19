<template>
  <body>
    <h1>Login</h1>
    <div>
      <h3>Email:</h3>
      <input v-model="userDto.email" type="text" placeholder="email@example.com">
    </div>
    <div>
      <h3>Password:</h3>
      <input v-model="userDto.password" type="password" placeholder="password">
    </div>
    <div v-if="wrong">You entered email or password wrong.</div>
    <div v-if="!isVerified">You can't login until an admin verifies your account.</div>
    <div class="bottomPart">
      <button @click="checkUser()">Login</button>
    </div>
    <div class="bottomPart">
      <!--<a href="http://localhost:3000/forgotpassword">Forgot password?</a>-->
      <button class="forgotPassword" @click="redirectPage('/forgotpassword')">Forgot password?</button>
    </div>
    <div class="bottomPart">
      <button @click="redirectPage('/signup')">Signup</button>
    </div>
  </body>
</template>

<script>
import UserService from "../services/UserService"

export default {
  data() {
    return {
      userDto: {
        email: "",
        password: "",
      },
      wrong: false,
      check: false,
      isVerified: true
    }
  },
  methods: {
    async checkUser() {
      const response = await UserService.checkUser(this.userDto)
      this.check = response.data
      let user
      if (this.check) {
        await UserService.getUser(this.userDto.email)
            .then(response => {
              user = response.data
            })
        if(user.verified) {
          localStorage.setItem("token", user.token)
          localStorage.setItem("role", user.role)
          localStorage.setItem('loginStatus', true);
          localStorage.setItem("userEmail", this.userDto.email)
          localStorage.setItem("refresh", true)
          this.redirectPage('/')
        } else {
          this.isVerified = false
        }
      } else {
        this.wrong = true
      }
    },
    redirectPage(path) {
      this.$router.replace(path);
    },
  },
}
</script>

<style scoped>
button {
  padding: 10px;
  font-size: 15px;
  width: 120px;
  margin: 20px;
}
body {
  text-align: center;
}
input {
  padding: 10px;
  font-size: 17px;
}
.bottomPart {
  margin-bottom: 15px;
}
.forgotPassword {
  background: none;
  border: none;
  padding: 0;
  text-decoration: underline;
  cursor: pointer;
  width: 30%;
  margin: 0 auto;
}
</style>