package org.sixtead.techrequests.user;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sixtead.techrequests.comments.Comment;
import org.sixtead.techrequests.group.Group;
import org.sixtead.techrequests.issue.Issue;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

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
    @OneToMany(mappedBy = "createdBy")
    private Set<Issue> createdIssues;
    @OneToMany(mappedBy = "assignedTo")
    private Set<Issue> assignedIssues;
    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;
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
