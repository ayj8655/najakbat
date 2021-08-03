package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.Message;

@Repository
public interface MessageDAO extends PagingAndSortingRepository<Message, Integer> {
	
	public Optional<Message> findByMessageNumber(int messageNumber);
	
	@Query("SELECT new Map(u.nickname as nickname, m.content as content, m.isRead as isRead, m.time as time) "
			+ "FROM message m JOIN user u "
			+ "ON m.userSender = u.id "
			+ "WHERE m.userSender = :userSender and m.owner = :owner ")
	public List<Object> findAllByUserSender(Pageable pageable, int userSender, int owner);
	
	@Query("SELECT new Map(u.nickname as nickname, m.content as content, m.isRead as isRead, m.time as time) "
			+ "FROM message m JOIN user u "
			+ "ON m.userSender = u.id "
			+ "WHERE m.userReceiver = :userReceiver and m.owner = :owner ")
	public List<Message> findAllByUserReceiver(int userReceiver, int owner);
	
	public void deleteByMessageNumber(int messageNumber);
}
