package com.mococo.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.CommentRecommend;
import com.mococo.common.model.CommentRecommendPK;

@Repository
public interface CommentRecommendDAO extends JpaRepository<CommentRecommend,CommentRecommendPK>{

	void deleteByCommentNumberAndUserNumber(int commentno, int userno);


}
