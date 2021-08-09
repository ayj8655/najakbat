package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.Post;

@Repository
public interface PostDAO extends JpaRepository<Post, Integer>{
	
	public Optional<Post> findPostByPostNumber(int no);
	public List<Post> findAll();
	public List<Post> findAllByPostType(int type);
	
	// limit은 어디부터 3개씩 가져올지 선택 no는 유저 번호
	@Query(value = "select * from post where user_number = ? order by date desc limit ?, 3 ", nativeQuery = true)
	public List<Post> findAllByUserNumber(int no,int limit);
	
	// 무한스크롤을 위한 dao
	@Query(value = "select * from post order by date desc limit ?, 3", nativeQuery = true)
	List<Post> findInfinitePost(int limit);
}
