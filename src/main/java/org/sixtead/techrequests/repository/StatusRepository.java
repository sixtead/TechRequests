package org.sixtead.techrequests.repository;

import org.sixtead.techrequests.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
