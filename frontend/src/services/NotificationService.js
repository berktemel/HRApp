import http from "../httpSource";

class NotificationService {
    getNotifications() {
        return http.get('/notifications')
    }

    getNotificationById(id) {
        return http.get(`/notifications/${id}`)
    }

    sendNotification(notification) {
        return http.post('/notifications', notification)
    }

    deleteNotificationFromAll(id) {
        return http.delete(`/notifications/${id}`)
    }

    deleteNotificationFrom(id, email) {
        return http.delete(`/users/${email}/notifications/${id}`)
    }
}

export default new NotificationService();