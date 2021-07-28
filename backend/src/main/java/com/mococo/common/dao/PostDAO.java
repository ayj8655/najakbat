package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.Post;

@Repository
public interface PostDAO extends JpaRepository<Post, Integer>{
	
	public Optional<Post> findPostByPostNumber(int no);
	public List<Post> findAll();
	public List<Post> findAllByPostType(int type);
	public List<Post> findAllByUserNumber(int no);
	
}
