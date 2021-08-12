package com.mococo.common.listener;

import javax.persistence.PostPersist;

import com.mococo.common.model.Message;

public class MessageListener {

	@PostPersist
	public void postPersist(Message message) {
		System.out.println("메시지" + message.getContent());	
	}
}
