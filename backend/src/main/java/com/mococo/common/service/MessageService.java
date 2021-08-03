package com.mococo.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.MessageDAO;
import com.mococo.common.model.Message;

@Service
public class MessageService {
	
	@Autowired
	MessageDAO messageDAO;
	
	public List<Object> findAllBySender(int page, int userSender, int owner) {
		return messageDAO.findAllByUserSender(PageRequest.of(Math.max(0, page), 20), userSender, owner);
	}
	
	public boolean insertMessage(Message message) {
		Optional<Message> messageOpt = messageDAO.findByMessageNumber(message.getMessageNumber());
		
		if(!messageOpt.isPresent()) {
			messageDAO.save(message);
			return true;
		} else {
			return false;
		}
	}
}
