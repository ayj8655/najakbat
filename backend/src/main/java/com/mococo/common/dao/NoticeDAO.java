package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mococo.common.model.Notice;

@Repository
public interface NoticeDAO extends JpaRepository<Notice, Integer>{

	public Optional<Notice> findByUserNumber(int userNumber);

	public Optional<Notice> findByNoticeNumber(int noticeNumber);

	public List<Notice> findAllByUserNumber(int userNumber);
	
	@Transactional
	public int deleteAllByNoticeNumberIn(List<Integer> noticeno);
	
	@Transactional
	public void deleteAllByUserNumber(int userNumber);

	
	
	

}
