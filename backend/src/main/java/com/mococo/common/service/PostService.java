package com.mococo.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.PostDAO;
import com.mococo.common.dao.PostRecommendDAO;
import com.mococo.common.model.Post;
import com.mococo.common.model.PostRecommend;
import com.mococo.common.model.PostRecommendPK;
import com.mococo.common.model.User;

@Service
public class PostService {
	
	@Autowired
	PostDAO postDAO;
	
	@Autowired
	PostRecommendDAO postrecommendDAO;
	
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
	
	// 유저 별로 게시글 쓴거 불러오기
	public List<Post> findPostUser(int no,int limit){
		List<Post> posts = postDAO.findAllByUserNumber(no,limit);
		return posts;
	}	
	
	public Post insertPost(Post post) {
		Optional<Post> ret = postDAO.findPostByPostNumber(post.getPostNumber());
		if(ret.isPresent()) {
			return null;
		}
		
		
		Post p = postDAO.save(post);
		return p;
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
	
	public boolean recommendPost(int postno, int userno) {
		Optional<Post> ret = postDAO.findPostByPostNumber(postno);

		// 추천할 post가 없는 경우 - 잘못된 접근
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
			postDAO.save(ret.get());
			
			// POST RECOMMNED 테이블에 이번에 누른 정보를 insert
			PostRecommend pr = new PostRecommend(postno,userno);
			postrecommendDAO.save(pr);
			return true;
		}
		
		// 이번 요청으로 추천을 취소 하는 경우
		else {
			// 게시글 테이블의 추천수 컬럼 -1
			ret.get().setRecommend(ret.get().getRecommend()-1);
			postDAO.save(ret.get());
			
			// POST RECOMMNED 테이블에 이번에 누른 정보를 delete
			postrecommendDAO.deleteByPostNumberAndUserNumber(postno, userno);
			return true;
		}

		
	}


}
