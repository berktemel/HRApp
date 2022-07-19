package ems.service;

import ems.module.Notification;
import ems.module.User;
import ems.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification sendNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification findByNotificationId(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if(notification.isPresent()) {
            return notification.get();
        } else {
            throw new RuntimeException("User with id " + id + " doesnt exist.");
        }
    }

    @Override
    public boolean isEmpty() {
        return notificationRepository.count() == 0;
    }

    @Override
    public void deleteByNotificationId(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if(notification.isPresent()) {
            notificationRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with id " + id + " doesnt exist.");
        }
    }

    @Override
    public Notification findBySender(User user) {
        return notificationRepository.findNotificationBySender(user);
    }

    @Override
    public void deleteAll() {
        notificationRepository.deleteAll();
    }
}
