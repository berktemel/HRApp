package ems.repository;

import ems.module.Notification;
import ems.module.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findNotificationBySender(User user);


}
