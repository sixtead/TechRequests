package org.sixtead.techrequests.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "issues")
public class Issue {

    @Id
    @SequenceGenerator(name = "seq_issues_id", sequenceName = "seq_issues_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_issues_id")
    private Long id;

    private String name;

    private String description;

    private String solution;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private Priority priority;

    private Timestamp dueTo;

    @ManyToMany(mappedBy = "issues")
    private Set<Tag> tags;

    @OneToMany(mappedBy = "issue")
    private Set<Comment> comments;

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public Issue() {
    }

    public Issue(String name, String description, String solution, User createdBy, User assignedTo, Status status, Priority priority, Timestamp dueTo) {
        this.name = name;
        this.description = description;
        this.solution = solution;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.status = status;
        this.priority = priority;
        this.dueTo = dueTo;
    }
}
