package org.sixtead.techrequests.user;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sixtead.techrequests.group.Group;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users_id")
    private Long id;
    private String username;
    private String passwordDigest;
    private String firstName;
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    public User() {
    }

    public User(String username, String passwordDigest, String firstName, String lastName, Group group) {
        this.username = username;
        this.passwordDigest = passwordDigest;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }
}
