package org.sixtead.techrequests.group;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import org.sixtead.techrequests.user.User;

@Data
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_groups_id")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "group")
    private Set<User> user;
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    public Group() {}

    public Group(String name) {
        this.name = name;
    }

}