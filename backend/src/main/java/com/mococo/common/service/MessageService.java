package com.mococo.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.MessageDAO;
import com.mococo.common.model.Message;
import com.mococo.common.model.UserCrop;

@Service
public class MessageService {
	
	@Autowired
	MessageDAO messageDAO;
	
	public boolean insertMessage(Message message) {
		List<Message> messageList = new ArrayList<Message>();
		
		Message message1 = new Message();
		message1.setUserSender(message.getUserSender());
		message1.setUserReceiver(message.getUserReceiver());
		message1.setOwner(message.getUserSender());
		message1.setContent(message.getContent());
		message1.setTime(message.getTime());
		message1.setRead(true);
		messageList.add(message1);
		Optional<Message> messageOpt1 = messageDAO.findByMessageNumber(message1.getMessageNumber());
		if(messageOpt1.isPresent()) return false;
		
		if(message.getUserSender() != message.getUserReceiver()) {
			Message message2 = new Message();
			message2.setUserSender(message.getUserSender());
			message2.setUserReceiver(message.getUserReceiver());
			message2.setOwner(message.getUserReceiver());
			message2.setContent(message.getContent());
			message2.setTime(message.getTime());
			message2.setRead(false);
			messageList.add(message2);
			Optional<Message> messageOpt2 = messageDAO.findByMessageNumber(message2.getMessageNumber());
			if(messageOpt2.isPresent()) return false;
		}
		
		messageDAO.saveAll(messageList);
		return true;
	}
	
	public boolean readMessage(int messageNumber) {
		Optional<Message> messageOpt = messageDAO.findByMessageNumber(messageNumber);
		
		if(messageOpt.isPresent()) {
			messageOpt.get().setRead(true);
			messageDAO.save(messageOpt.get());
			return true;
		} else {
			return false;
		}
	}
	
	public void deleteMessage(int messageNumber) {
		messageDAO.deleteByMessageNumber(messageNumber);
	}
	
	public List<Object> findAllBySender(int page, int userSender, int owner) {
		return messageDAO.findAllByUserSender(PageRequest.of(Math.max(0, page), 20), userSender, owner);
	}
	
	public List<Object> findAllByReceiver(int page, int userReceiver, int owner) {
		return messageDAO.findAllByUserReceiver(PageRequest.of(Math.max(0, page), 20), userReceiver, owner);
	}
	
	public List<Object> findAllByOwner(int page, int owner) {
		return messageDAO.findAllByOwner(PageRequest.of(Math.max(0, page), 20), owner);
	}
}
