package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.Notice;
import com.mococo.common.model.User;


@Repository
public interface NoticeDAO extends JpaRepository<Notice, Integer>{

	public Optional<Notice> findByUserNumber(int userNumber);
	
	
	
	

}
