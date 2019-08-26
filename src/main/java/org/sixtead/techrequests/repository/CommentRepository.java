package org.sixtead.techrequests.repository;

import org.sixtead.techrequests.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
