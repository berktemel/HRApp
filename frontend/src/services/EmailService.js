import http from "../httpSource";

class EmailService {
    sendEmail(email) {
        return http.post("/sendEmail", email);
    }
}

export default new EmailService();