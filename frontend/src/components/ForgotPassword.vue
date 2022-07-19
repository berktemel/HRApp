<template>
  <h1>Forgot Password</h1>
  <div v-if="notSent">
    <p>We will send an email with a link to reset your password.</p>
    <ul>
      <li>
        <label><strong>Email:</strong></label>
        <input v-model="user.email" placeholder="email@example.com">
      </li>
    </ul>
    <button @click="sendEmail()">Send Email</button>
    <button @click="pageRedirect()">Cancel</button>
  </div>
  <div v-else class="msg">
    <div>Email sent.</div>
    <button @click="pageRedirect()">Return to Login page</button>
  </div>
</template>

<script>
import EmailService from "../services/EmailService"
import UserService from "../services/UserService"
function hashCode(str) {
  let hash = 0;
  for (let i = 0, len = str.length; i < len; i++) {
    let chr = str.charCodeAt(i);
    hash = (hash << 5) - hash + chr;
    hash |= 0; // Convert to 32bit integer
  }
  return hash;
}
export default {
  name: "ForgotPassword",
  data() {
    return {
      notSent: true,
      emailDetails: {
        to: "",
        subject: "Password reset",
        body: "Click the following link to reset your password. http://localhost:3000/resetpassword/"
      },
      user: {
        email: "",
        resetPwToken: "",
      }
    }
  },
  methods: {
    pageRedirect() {
      this.$router.replace('/login');
    },
    sendEmail() {
      this.emailDetails.to = this.user.email
      const hash = hashCode(this.emailDetails.to).toString();
      this.user.resetPwToken = hash
      localStorage.setItem("resetKey", hash)
      this.emailDetails.body += hash;
      UserService.setPwToken(this.user)
      EmailService.sendEmail(this.emailDetails)
      this.notSent = false
    }
  }
}
</script>

<style scoped>
p {
  margin-bottom: 30px;
}
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
</style>