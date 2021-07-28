package com.mococo.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.PostDAO;
import com.mococo.common.model.Post;

@Service
public class PostService {
	
	@Autowired
	PostDAO postDAO;
	
	// 게시글 번호로 해당 Post 리턴
	public Optional<Post> findPostByPostNumber(int no){
		Optional<Post> post = postDAO.findPostByPostNumber(no);
		
		return post;
	}
	
	public List<Post> findAllPost(){
		List<Post> posts = postDAO.findAll();
		return posts;
	}
	
	// 무한스크롤으로 포스트를 뽑아주는 service
	public List<Post> findInfinitePost(int limit){
		List<Post> posts = postDAO.findInfinitePost(limit);
		return posts;
	}

	
	
	public List<Post> findPostType(int type){
		List<Post> posts = postDAO.findAllByPostType(type);
		return posts;
	}	
	
	public List<Post> findPostUser(int no){
		List<Post> posts = postDAO.findAllByUserNumber(no);
		return posts;
	}	
	
	public boolean insertPost(Post post) {
		Optional<Post> ret = postDAO.findPostByPostNumber(post.getPostNumber());
		if(ret.isPresent()) {
			return false;
		}
		postDAO.save(post);
		return true;
	}
	
	public boolean updatePost(Post post) {
		Optional<Post> ret = postDAO.findPostByPostNumber(post.getPostNumber());
		
		// update할 post가 없는 경우
		if(!ret.isPresent()) {
			return false;
		}
		
		postDAO.save(post);
		return true;
	}
	
	public boolean deletePost(int no) {
		Optional<Post> ret = postDAO.findPostByPostNumber(no);
		
		// delete할 post가 없는 경우
		if(!ret.isPresent()) {
			return false;
		}
		
		postDAO.deleteById(no);
		return true;
	}
	
//	public boolean recommendPost(int no) {
//		Optional<Post> ret = postDAO.findPostByPostNumber(no);
//		
//		// 추천할 post가 없는 경우
//		if(!ret.isPresent()) {
//			return false;
//		}
//		ret.get().setRecommend(ret.get().getRecommend()+1);
//		postDAO.save(ret.get());
//		return true;
//		
//	}
}
