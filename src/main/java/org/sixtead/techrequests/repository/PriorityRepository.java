package org.sixtead.techrequests.repository;

import org.sixtead.techrequests.domain.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityRepository extends JpaRepository<Priority, Long> {
}
