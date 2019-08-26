package org.sixtead.techrequests.repository;

import org.sixtead.techrequests.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
