package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.Comment;

@Repository
public interface CommentDAO extends JpaRepository<Comment, Integer> {

	// 게시글 번호를 넣으면 거기에 적힌 댓글들을 모두 리턴하는 함수
	List<Comment> findAllByPostNumber(int no);
	Optional<Comment> findCommentByCommentNumber(int postNumber);
	
	
}
