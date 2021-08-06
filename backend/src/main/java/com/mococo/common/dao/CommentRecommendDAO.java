package com.mococo.common.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.CommentRecommend;
import com.mococo.common.model.CommentRecommendPK;

@Repository
public interface CommentRecommendDAO extends JpaRepository<CommentRecommend,CommentRecommendPK>{
	
	// transactional 추가안하면 오류떠서 추가했음.
	@Transactional
	void deleteByCommentNumberAndUserNumber(int commentno, int userno);

}
