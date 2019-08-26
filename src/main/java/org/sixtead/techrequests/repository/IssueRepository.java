package org.sixtead.techrequests.repository;

import org.sixtead.techrequests.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
