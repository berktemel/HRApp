import http from "../httpSource";

class UserService {
    getUsers() {
        return http.get('/users');
    }


    getUser(email) {
        return http.get(`/users/${email}`);
    }

    addUser(user) {
        return http.post('/users', user);
    }

    setPwToken(user) {
        return http.put('/', user)
    }

    checkToken(email) {
        return http.post(`/users/${email}/token`)
    }

    verifyUser(id) {
        return http.put(`/users/${id}/verification`)
    }

    getUserByResetPwToken(user) {
        return http.get(`/?hash=${user}`)
    }

    getNotifications(email) {
        return http.get(`/users/${email}/notifications`)
    }

    setNotificationAsRead(email, id) {
        return http.put(`/users/${email}/notifications/${id}`)
    }

    editUser(user) {
        return http.put('/users', user)
    }

    checkUser(user) {
        return http.post('/users/login', user)
    }

    expireToken(id) {
        return http.put(`/users/${id}`)
    }

    deleteUser(id) {
        return http.delete(`/users/${id}`);
    }

}

export default new UserService();