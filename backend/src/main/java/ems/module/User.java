package ems.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "lastName")
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    @Column(name = "password")
    private String password;

    @Column(columnDefinition = "varchar(255) default 'user'")
    private String role;

    @Column
    private String token;

    @Column
    private String resetPwToken;

    @JsonIgnore
    @ManyToMany()
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_notification",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "notification_id"))
    private List<Notification> notifications = new ArrayList<>();

    @Column(columnDefinition = "boolean default false")
    private boolean verified;

    public void copy(UserDto userDto) {
        this.name = userDto.getName();
        this.lastName = userDto.getLastName();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.role = userDto.getRole();
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public Notification getNotificationById(Long id) {
        for(Notification notification: notifications) {
            if(notification.getNotificationId().equals(id)) {
                return notification;
            }
        }
        return null;
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void deleteNotification(Notification notification) {
        notifications.remove(notification);
    }
    @Override
    public String toString() {
        return "{\n\tid: " + this.id + "\n\tname: " + this.name + "\n\tlastName: " + this.lastName +
                "\n\temail: " + this.email + "\n\trole: " + this.role + "\n}";
    }
}
