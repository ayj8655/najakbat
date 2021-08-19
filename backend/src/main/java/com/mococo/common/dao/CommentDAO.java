package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.Comment;

@Repository
public interface CommentDAO extends JpaRepository<Comment, Integer> {
	
	//List<Comment> findAllByPostNumber(int no);
	Optional<Comment> findCommentByCommentNumber(int postNumber);
	
	
	// 게시글 번호를 넣으면 거기에 적힌 댓글들을 모두 리턴하는 함수
	// 입력이 들어오면
	// 1댓글
	//  1댓글의 대댓글
	//  1댓글의 두번째 대댓글
	// 2댓글
	// 순서로 sorting
	
	@Query(value = 
			"SELECT c0.*"
			+"     , CONCAT("
			+"			 IFNULL(LPAD(c1.comment_number, 100, 0), '') "
			+"             , IFNULL(LPAD(c0.comment_number, 100, 0), '') "
			+"             ) x"
			+"  FROM comment c0"
			+"  LEFT OUTER JOIN comment c1 ON c1.comment_number = c0.parent"
			+" WHERE c0.post_number =?"
			+" ORDER BY x;" 
			, nativeQuery = true	
			)
	List<Comment> findAllByPostNumber(int no);

	Optional<Comment> findCommentByParent(int parent);
	List<Comment> findAllByParent(int parent);
}
