package com.mococo.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.CommentDAO;
import com.mococo.common.dao.CommentRecommendDAO;
import com.mococo.common.dao.PostRecommendDAO;
import com.mococo.common.model.Comment;
import com.mococo.common.model.CommentRecommend;
import com.mococo.common.model.Post;
import com.mococo.common.model.PostRecommend;
import com.mococo.common.model.User;

@Service
public class CommentService {
	
	@Autowired
	CommentDAO commentDAO;

	@Autowired
	CommentRecommendDAO commentrecommendDAO;
	
	public List<Comment> findAllByPostNumber(int no) {
		List<Comment> comments = commentDAO.findAllByPostNumber(no);
		
		return comments;
	}

	public boolean insertComment(Comment comment) {
		Optional<Comment> ret = commentDAO.findCommentByCommentNumber(comment.getCommentNumber());
		if(ret.isPresent()) {
			return false;
		}
		commentDAO.save(comment);
		return true;
	}

	public boolean updateComment(Comment comment) {
		Optional<Comment> ret = commentDAO.findCommentByCommentNumber(comment.getCommentNumber());
		if(!ret.isPresent()) {
			return false;
		}
		commentDAO.save(comment);
		return true;
	}

	public boolean deleteComment(int no) {
		Optional<Comment> ret = commentDAO.findCommentByCommentNumber(no);
		
		// delete할 post가 없는 경우
		if(!ret.isPresent()) {
			return false;
		}
		
		commentDAO.deleteById(no);
		return true;
	}

	public boolean recommendComment(int commentno, int userno) {
		Optional<Comment> ret = commentDAO.findCommentByCommentNumber(commentno);

		// 추천할 comment가 없는 경우 - 잘못된 접근
		if(!ret.isPresent()) {
			return false;
		}
		
		boolean isRecommend= false;
		for(User user :ret.get().getUsers()) {
			// 이미 추천되어있으면 추천 취소
			if(user.getUserNumber() == userno) {
				isRecommend =true;
			}

		}
		// 이번 요청으로 추천을 누르는 경우
		if(isRecommend == false) {
			// 게시글 테이블의 추천수 컬럼 +1
			ret.get().setRecommend(ret.get().getRecommend()+1);
			commentDAO.save(ret.get());
			
			// COMMENT RECOMMNED 테이블에 이번에 누른 정보를 insert
			CommentRecommend pr = new CommentRecommend(commentno,userno);
			commentrecommendDAO.save(pr);
			return true;
		}
		
		// 이번 요청으로 추천을 취소 하는 경우
		else {
			// 게시글 테이블의 추천수 컬럼 -1
			ret.get().setRecommend(ret.get().getRecommend()-1);
			commentDAO.save(ret.get());
			
			// POST RECOMMNED 테이블에 이번에 누른 정보를 delete
			commentrecommendDAO.deleteByCommentNumberAndUserNumber(commentno, userno);
			return true;
		}

	}


	
	
}
