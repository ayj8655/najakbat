package com.mococo.common.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.CommentRecommend;
import com.mococo.common.model.CommentRecommendPK;

@Repository
public interface CommentRecommendDAO extends JpaRepository<CommentRecommend,CommentRecommendPK>{
	
	// transactional 추가안하면 오류떠서 추가했음.
	@Transactional
	void deleteByCommentNumberAndUserNumber(int commentno, int userno);
	
	// 게시글 안에 댓글 중 특정 유저가 좋아요 누른 댓글 번호 리스트
	@Query(value="SELECT cr.comment_number as commentNumber "
			+ "FROM comment_recommend cr "
			+ "LEFT JOIN comment c "
			+ "ON cr.comment_number = c.comment_number "
			+ "LEFT JOIN post p "
			+ "ON c.post_number = p.post_number "
			+ "WHERE p.post_number = :postno and cr.user_number = :userno ", nativeQuery = true)
	public List<Object> findAllByUserNumber(int postno, int userno);

}
