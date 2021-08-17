package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.PostRecommend;
import com.mococo.common.model.PostRecommendPK;


@Repository
public interface PostRecommendDAO extends JpaRepository<PostRecommend, PostRecommendPK>{
	@Transactional
	public void deleteByPostNumberAndUserNumber(int postno, int userno);

	public Optional<PostRecommend> findByPostNumberAndUserNumber(int postno, int userno);

	
	@Query(value = "SELECT new Map(pr.postNumber as postNumber) "
			+ "FROM post_recommend pr "
			+ "WHERE user_number = :userno ")
	public List<Object> findLikePostById(int userno);
}
