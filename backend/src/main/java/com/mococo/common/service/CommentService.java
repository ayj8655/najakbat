package com.mococo.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.CommentDAO;
import com.mococo.common.dao.CommentRecommendDAO;
import com.mococo.common.dao.PostDAO;
import com.mococo.common.model.Comment;
import com.mococo.common.model.CommentRecommend;
import com.mococo.common.model.Post;
import com.mococo.common.model.User;

@Service
public class CommentService {
	
	@Autowired
	CommentDAO commentDAO;
	
	@Autowired
	PostDAO postDAO;
	
	@Autowired
	CommentRecommendDAO commentrecommendDAO;
	
	public List<Comment> findAllByPostNumber(int no) {
		List<Comment> comments = commentDAO.findAllByPostNumber(no);
		
		return comments;
	}
	
	public List<Object> findAllByUserNumber(int postno, int userno) {
		List<Object> comments = commentrecommendDAO.findAllByUserNumber(postno, userno);
		
		return comments;
	}
	
	
	public boolean insertComment(Comment comment) {
		Optional<Comment> ret = commentDAO.findCommentByCommentNumber(comment.getCommentNumber());
		if(ret.isPresent()) {
			return false;
		}
		

		System.out.println(1);
		// 게시글에 댓글이 달리거나 댓글에 대댓글 달리면 게시글에 comment_count+1
		Optional<Post> post = postDAO.findPostByPostNumber(comment.getPostNumber());

		System.out.println(2);
		int commentCount = post.get().getCommentCount();

		System.out.println(3);
		post.get().setCommentCount(commentCount+1);

		System.out.println(4);
		postDAO.save(post.get());
		System.out.println(5);


		// 대댓글이면 바로 상위 댓글에 comment_count+1
		if(comment.getParent()!=0) {
			Optional<Comment> c = commentDAO.findCommentByCommentNumber(comment.getParent());
			commentCount = c.get().getCommentCount();
			c.get().setCommentCount(commentCount+1);
			commentDAO.save(c.get());
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
		List<Comment> list = commentDAO.findAllByParent(no); // 해당 댓글의 대댓글들을 리스트로 받아서 지우기
		
		
		
		// delete할 post가 없는 경우
		if(!ret.isPresent()) {
			return false;
		}
		commentDAO.deleteById(no);
		if(list!=null) {
			for(Comment c : list) {
				commentDAO.deleteById(c.getCommentNumber());
			}
			
		}
		Comment comment = ret.get();
		// 대댓글을 지웠으면 그 부모의 comment_count-1을한다.
		if(comment.getParent()!=0) {
			int parent = comment.getParent();
			Optional<Comment> parentComment = commentDAO.findCommentByCommentNumber(parent);
			Comment parentcomment = parentComment.get();
			parentcomment.setCommentCount(parentcomment.getCommentCount()-1);
			commentDAO.update(parentcomment);
		}
		
		return true;
	}

	public int recommendComment(int commentno, int userno) {
		Optional<Comment> ret = commentDAO.findCommentByCommentNumber(commentno);

		// 추천할 comment가 없는 경우 - 잘못된 접근
		if(!ret.isPresent()) {
			return -1;
		}
		
		boolean isRecommend= false;
		Comment comment = ret.get();
		List<User> users = comment.getUsers();
		
		for(User user : users) {
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
			return 1;
		}
		
		// 이번 요청으로 추천을 취소 하는 경우
		else {
			// 게시글 테이블의 추천수 컬럼 -1
			ret.get().setRecommend(ret.get().getRecommend()-1);
			commentDAO.save(ret.get());
			
			// POST RECOMMNED 테이블에 이번에 누른 정보를 delete
			commentrecommendDAO.deleteByCommentNumberAndUserNumber(commentno, userno);
			return 0;
		}

	}


	
	
}
