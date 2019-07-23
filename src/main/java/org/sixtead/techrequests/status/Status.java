package org.sixtead.techrequests.status;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sixtead.techrequests.issue.Issue;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "statuses")
public class Status {

    @Id
    @SequenceGenerator(name = "seq_statuses_id", sequenceName = "seq_statuses_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_statuses_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "status")
    private Set<Issue> issues;

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public Status() {
    }

    public Status(String name) {
        this.name = name;
    }
}
