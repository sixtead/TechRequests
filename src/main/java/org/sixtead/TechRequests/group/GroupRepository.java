package org.sixtead.techrequests.group;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GroupRepository extends CrudRepository<Group, Long> {
    Optional<Group> findFirstByName(String name);
}