package org.sixtead.techrequests.group;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sixtead.techrequests.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @OneToMany(mappedBy = "group")
    private Set<User> users;

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