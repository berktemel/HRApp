package ems.controller;

import ems.module.*;
import ems.service.EmailService;
import ems.service.NotificationService;
import ems.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        try {
            List<NotificationDto> notifications = notificationService.findAll()
                    .stream().map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/notifications/{id}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable("id") Long id) {
        try {
            Notification notification = notificationService.findByNotificationId(id);
            return new ResponseEntity<>(convertToDto(notification), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/notifications")
    public ResponseEntity<NotificationDto> sendNotification(@RequestBody NotificationDto notificationDto) {
        try {
            List<User> users = userService.findAll();
            for(User user: users) {
                NotificationDto copy = new NotificationDto();
                copy.copy(notificationDto);
                user.addNotification(convertToEntity(copy));
                userService.save(user);
            }
            return new ResponseEntity<>(notificationDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<HttpStatus> deleteNotificationFromAll(@PathVariable("id") Long id) {
        try {
            List<User> users = userService.findAll();
            Notification notification = notificationService.findByNotificationId(id);
            for(User user: users) {
                user.deleteNotification(notification);
                userService.save(user);
            }
            notificationService.deleteByNotificationId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{email}/notifications/{id}")
    public ResponseEntity<HttpStatus> deleteNotificationFromOne(@PathVariable("id") Long id, @PathVariable String email) {
        try {
            Notification notification = notificationService.findByNotificationId(id);
            User user = userService.findByEmail(email);
            user.deleteNotification(notification);
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/notifications")
    public ResponseEntity<HttpStatus> deleteAllNotifications() {
        try {
            List<Notification> notifications = notificationService.findAll();
            for(Notification notification: notifications) {
                deleteNotificationFromAll(notification.getNotificationId());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void addNotificationToUsers(Notification notification) {
        List<User> users = userService.findAll();
        for(User user: users) {
            user.addNotification(notification);
            userService.save(user);
        }
    }

    private void deleteNotificationFrom(User user, Notification notification) {
        user.deleteNotification(notification);
    }

    private NotificationDto convertToDto(Notification notification) {
        return modelMapper.map(notification, NotificationDto.class);
    }

    private Notification convertToEntity(NotificationDto notificationDto) {
        return modelMapper.map(notificationDto, Notification.class);
    }

    private void sendNotificationMail(Notification notification) {
        List<User> users = userService.findAll();
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setSubject("Notification mail");
        emailDetails.setBody("From: " + notification.getSender() +
                "\nSubject: " + notification.getSubject() +
                "\nBody: " + notification.getBody());
        for(User user: users) {
            emailDetails.setTo(user.getEmail());
            emailService.sendEmail(emailDetails);
        }
    }
}
