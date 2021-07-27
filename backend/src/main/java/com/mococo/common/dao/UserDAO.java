package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.User;


@Repository
public interface UserDAO extends JpaRepository<User, Integer>{

	public Optional<User> findById(String id);
	
	public List<User> findByPassword(String password);
	
	

}
