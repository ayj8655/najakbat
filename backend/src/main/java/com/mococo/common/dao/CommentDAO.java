package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.Comment;

@Repository
public interface CommentDAO extends JpaRepository<Comment, Integer> {

	List<Comment> findAllByPostNumber(int no);
	Optional<Comment> findCommentByCommentNumber(int postNumber);
	
	
}
