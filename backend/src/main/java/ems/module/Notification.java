package ems.module;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "notification")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notificationId;

    @Column
    private String sender;

    @ManyToMany(mappedBy = "notifications")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    private List<User> receiver;

    @Column
    private String subject;

    @Column
    private String body;

    @Column(columnDefinition = "boolean default false")
    private boolean readStatus;
}
