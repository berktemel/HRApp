package ems.module;

import lombok.Data;

@Data
public class NotificationDto {
    private Long notificationId;
    private String sender;
    private String subject;
    private String body;
    private boolean readStatus;

    public void copy(NotificationDto notificationDto) {
        this.sender = notificationDto.getSender();
        this.subject = notificationDto.getSubject();
        this.body = notificationDto.getBody();
        this.readStatus = notificationDto.isReadStatus();
    }
}
