<template>
  <i class="el-icon-delete" @click="deleteNotification"></i>
  <h5 class="width">From: {{notification.sender}}</h5>
  <h5 class="width">Subject: {{notification.subject}}</h5>
  <p class="width" v-show="expanded">{{notification.body}}</p>
</template>

<script>
import NotificationService from "../services/NotificationService";
import UserService from "../services/UserService";
export default {
  name: "NotificationList",
  props: ['notification', 'index', 'unreadNumber'],
  data() {
    return {
      expanded: false,
      read: false,
    }
  },
  methods: {
    updateExpanded() {
      this.expanded = !this.expanded
      return this.expanded
    },
    deleteNotification() {
      if(!this.notification.readStatus) {
        this.$parent.decrementUnreadNumber()
      }
      NotificationService.deleteNotificationFrom(
          this.notification.notificationId,
          (localStorage.getItem("userEmail")))
      this.$parent.spliceNotification(this.index)
    },
  }
}
</script>

<style scoped>
.el-icon-delete {
  float: right;
  cursor: pointer;
}

.width {
  width: 380px;
  margin: 0;
}
</style>