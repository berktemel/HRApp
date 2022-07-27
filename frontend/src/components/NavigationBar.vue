<template>
  <nav>
    <el-menu class="menu" mode="horizontal" :default-active="$route.path" text-color="white"
    background-color="#696969FF" active-text-color="red">
      <el-menu-item index="/" @click="redirectPage('/')">
        Employees
      </el-menu-item>
      <el-menu-item index="/departments" @click="redirectPage('/departments')">
        Departments
      </el-menu-item>
      <el-submenu v-if="this.isAdmin" index="3">
        <template #title>Users</template>
          <el-menu-item index="/users" @click="redirectPage('/users')">
            Verified
          </el-menu-item>
          <el-menu-item index="/users/pending" @click="redirectPage('/users/pending')">
            Pending
          </el-menu-item>
      </el-submenu>
      <el-menu-item index="/login" @click="logout();redirectPage('/login')">
        Logout
      </el-menu-item>
      <span style="cursor: pointer" @click="changeSidebar">
        <el-badge :value="numberOfUnread" class="notificationBadge" type="danger">
          <i class="el-icon-message-solid"></i>
        </el-badge>
      </span>
    </el-menu>
  </nav>
  <div class="w3-sidebar w3-bar-block w3-border-right" style="display:none" id="mySidebar">
    <div style="height: 35px">
      <span style="font-size: 20px"><strong>Notifications</strong></span>
      <button style="float: right; padding: 8px" v-if="isAdmin" @click="changeAddNotification(); setSender()">
        Send Notification
      </button>
    </div>
    <div v-if="!addNotification" v-for="(notification, index) in notifications" :key="notification.notificationId"
         style="height: 60px" class="notificationCss"
         :class="{'readClass': notification.readStatus,
         'notReadClass': !notification.readStatus}"
         :id="'index' + index"
         @click="updateExpand(index); setNotificationAsRead(notification)">
      <NotificationList :notification="notification" :index="index" :unreadNumber="unreadNumber" ref="update" />
    </div>
    <div v-else>
      <div>
        <span>From: {{newNotification.sender}}</span>
      </div>
      <div>
        <span>Subject: </span>
        <input v-model="newNotification.subject" placeholder="Subject">
      </div>
      <div>
        <span>Body: </span>
        <input v-model="newNotification.body" placeholder="Body">
      </div>
      <button @click="sendNotification">Send</button>
      <button @click="changeAddNotification">Cancel</button>
    </div>
  </div>
</template>

<script>
import UserService from "../services/UserService";
import NotificationList from "./NotificationList.vue";
import NotificationService from '../services/NotificationService'
import { ElSubmenu, ElMenuItem } from 'element-plus'


export default {
  name: "NavigationBar",
  components: {
    NotificationList,
    ElSubmenu,
    ElMenuItem
  },
  data() {
    return {
      sidebar: false,
      user: [],
      notifications: [],
      unreadNumber: 0,
      addNotification: false,
      newNotification: {
        sender: "",
        subject: "",
        body: ""
      }
    }
  },
  methods: {
    logout() {
      const userEmail = localStorage.getItem("userEmail")
      UserService.getUser(userEmail)
          .then(response => {
            UserService.expireToken(response.data.id)
          })
      localStorage.removeItem("userEmail")
      localStorage.removeItem("token")
      localStorage.loginStatus = false;
      localStorage.setItem("refresh", true)
    },
    redirectPage(path) {
      this.$router.replace(path);
    },
    changeSidebar() {
      this.sidebar = !this.sidebar
      this.sidebar ?
          document.getElementById("mySidebar").style.display = "block":
          document.getElementById("mySidebar").style.display = "none"
    },
    async getNotifications() {
      await UserService.getNotifications(localStorage.getItem("userEmail"))
          .then(response => {
            this.notifications = response.data
          })
      for(let i = 0; i < this.notifications.length; i++) {
        if(!this.notifications[i].readStatus) {
          this.unreadNumber++;
        }
      }
    },
    expandNotification(index) {
      document.getElementById('index' + index).style.height = "100px"
    },
    minimizeNotification(index) {
      document.getElementById('index' + index).style.height = "60px"
    },
    updateExpand(index) {
      this.$refs.update[index].updateExpanded() ?
          this.expandNotification(index)
          : this.minimizeNotification(index)
    },
    setNotificationAsRead(notification) {
      if(!notification.readStatus) {
        UserService.setNotificationAsRead(localStorage.getItem("userEmail"), notification.notificationId)
        notification.readStatus = true
        this.unreadNumber--;
      }
    },
    spliceNotification(index) {
      this.notifications.splice(index, 1)
    },
    async getUser() {
      await UserService.getUser(localStorage.getItem("userEmail"))
          .then(response => {
            this.user = response.data
          })
    },
    changeAddNotification() {
      this.addNotification = !this.addNotification
    },
    sendNotification() {
      NotificationService.sendNotification(this.newNotification)
      this.changeAddNotification()
      this.getNotifications()
    },
    async setSender() {
      await this.getUser()
      this.newNotification.sender = this.user.name + " " + this.user.lastName
    },
    decrementUnreadNumber() {
      this.unreadNumber--
    }
  },
  computed: {
    isAdmin() {
      return (localStorage.getItem("role") === "admin")
    },
    numberOfUnread() {
      return this.unreadNumber
    },
    isZero() {
      return this.unreadNumber === 0
    }
  },
  mounted() {
    this.getNotifications()
  }
}
</script>

<style scoped>
nav {
  background-color: darkgray;
  font-size: 30px;
}
.link {
  text-decoration: none;
  color: white;
}
.link:hover {
  text-decoration: underline;
}
.el-icon-message-solid {
  font-size: 45px;
  float: right;
  position: relative;
  color: white;
  left: 10px;
  bottom: 8px;
}
.notificationBadge {
  margin-top: 14px;
  margin-right: 17px;
  float: right;
}
.notificationCss {
  border: 1px solid black;
  border-collapse: collapse;
}
.w3-sidebar{
  height:100%;
  width:400px;
  background-color:#fff;
  position:fixed;
  z-index:1;
  overflow:auto;
  right:0;
}
.w3-bar-block .w3-bar-item {
  width:100%;
  display:block;
  padding:8px 16px;
  text-align:left;
  border:none;
  white-space:normal;
  float:none;
  outline:0;
}

.w3-border-right {
  border-left:1px solid #ccc;
}

.readClass {
  background-color: darkgray;
}

.notReadClass {
  background-color: white;
}
.el-menu {
  margin: 0 auto;
}
.el-menu-item:hover {
  background-color: black !important;
}

</style>
