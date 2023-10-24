package com.peterson.helpdesk.repositories;

import com.peterson.helpdesk.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByRecommendation_Id(Integer ticketId);
}
