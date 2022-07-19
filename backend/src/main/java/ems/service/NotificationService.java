package ems.service;

import ems.module.Notification;
import ems.module.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {

    Notification sendNotification(Notification notification);

    void save(Notification notification);

    List<Notification> findAll();

    Notification findByNotificationId(Long id);

    boolean isEmpty();

    void deleteByNotificationId(Long id);

    Notification findBySender(User user);

    void deleteAll();
}
