package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mococo.common.model.Message;

@Repository
public interface MessageDAO extends PagingAndSortingRepository<Message, Integer> {
	
	public Optional<Message> findByMessageNumber(int messageNumber);
	
	@Query("SELECT new Map(m.messageNumber as messageNumber, s.nickname as senderNickname, r.nickname as receiverNickname, m.content as content, m.isRead as isRead, m.time as time) "
			+ "FROM message m "
			+ "LEFT JOIN user s "
			+ "ON m.userSender = s.userNumber "
			+ "LEFT JOIN user r "
			+ "ON m.userReceiver = r.userNumber "
			+ "WHERE m.userSender = :userSender and m.owner = :owner ")
	public List<Object> findAllByUserSender(Pageable pageable, int userSender, int owner);
	
	@Query("SELECT new Map(m.messageNumber as messageNumber, s.nickname as senderNickname, r.nickname as receiverNickname, m.content as content, m.isRead as isRead, m.time as time) "
			+ "FROM message m "
			+ "LEFT JOIN user s "
			+ "ON m.userSender = s.userNumber "
			+ "LEFT JOIN user r "
			+ "ON m.userReceiver = r.userNumber "
			+ "WHERE m.userReceiver = :userReceiver and m.owner = :owner ")
	public List<Object> findAllByUserReceiver(Pageable pageable, int userReceiver, int owner);
	
	@Query("SELECT new Map(m.messageNumber as messageNumber, s.nickname as senderNickname, r.nickname as receiverNickname, m.content as content, m.isRead as isRead, m.time as time) "
			+ "FROM message m "
			+ "LEFT JOIN user s "
			+ "ON m.userSender = s.userNumber "
			+ "LEFT JOIN user r "
			+ "ON m.userReceiver = r.userNumber "
			+ "WHERE m.owner = :owner ")
	public List<Object> findAllByOwner(Pageable pageable, int owner);
	
	@Transactional
	public void deleteByMessageNumber(int messageNumber);
	
	@Transactional
	public int deleteAllByMessageNumberIn(List<Integer> messageNumberList);
}
