package org.sixtead.techrequests.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sixtead.techrequests.domain.Comment;
import org.sixtead.techrequests.domain.Group;
import org.sixtead.techrequests.domain.Issue;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(name = "seq_users_id", sequenceName = "seq_users_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users_id")
    private Long id;

    private String username;

    private String passwordDigest;

    private Boolean enabled;

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

    @Column(updatable = false)
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
