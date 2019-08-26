package org.sixtead.techrequests.repository;

import org.sixtead.techrequests.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
