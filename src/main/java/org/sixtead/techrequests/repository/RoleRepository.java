package org.sixtead.techrequests.repository;

import org.sixtead.techrequests.domain.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findFirstByName(String name);
    List<Role> findAll();
}
