package org.sixtead.techrequests.comments;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sixtead.techrequests.issue.Issue;
import org.sixtead.techrequests.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @SequenceGenerator(name = "seq_comments_id", sequenceName = "seq_comments_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_comments_id")
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public Comment() {
    }

    public Comment(String content, User user, Issue issue) {
        this.content = content;
        this.user = user;
        this.issue = issue;
    }
}
