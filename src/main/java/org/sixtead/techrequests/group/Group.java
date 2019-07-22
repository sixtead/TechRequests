package org.sixtead.techrequests.group;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sixtead.techrequests.user.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @SequenceGenerator(name = "seq_groups_id", sequenceName = "seq_groups_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_groups_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "group")
    private Set<User> user;

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public Group() {}

    public Group(String name) {
        this.name = name;
    }

}