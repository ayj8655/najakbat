package com.mococo.common.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.PostRecommend;
import com.mococo.common.model.PostRecommendPK;


@Repository
public interface PostRecommendDAO extends JpaRepository<PostRecommend, PostRecommendPK>{
	@Transactional
	public void deleteByPostNumberAndUserNumber(int postno, int userno);

	public Optional<PostRecommend> findByPostNumberAndUserNumber(int postno, int userno);

}
