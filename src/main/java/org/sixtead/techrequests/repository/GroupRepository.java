package org.sixtead.techrequests.repository;

import org.sixtead.techrequests.domain.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Long> {
}