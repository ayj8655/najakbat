package com.mococo.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.CommentDAO;
import com.mococo.common.model.Comment;
import com.mococo.common.model.Post;

@Service
public class CommentService {
	
	@Autowired
	CommentDAO commentDAO;

	
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

	public boolean recommendComment(int comment_number, int user_number) {

		return true;
	}


	
	
}
