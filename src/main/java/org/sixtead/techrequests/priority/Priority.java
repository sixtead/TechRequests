package org.sixtead.techrequests.priority;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sixtead.techrequests.issue.Issue;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "priorities")
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_priorities_id")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "priority")
    private Set<Issue> issues;
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    public Priority() {
    }

    public Priority(String name) {
        this.name = name;
    }
}
