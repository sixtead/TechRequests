package org.sixtead.techrequests.repository;

import org.sixtead.techrequests.domain.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GroupRepository extends CrudRepository<Group, Long> {
    Optional<Group> findFirstByName(String name);
}