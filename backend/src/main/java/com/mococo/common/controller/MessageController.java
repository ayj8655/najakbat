package com.mococo.common.controller;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mococo.common.model.Message;
import com.mococo.common.service.MessageService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8080/swagger-ui.html/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/message")
public class MessageController {
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ERROR = "error";
	
	@Autowired
	MessageService messageService;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ApiOperation(value = "메시지 전송")
	public ResponseEntity<String> insertCrop(@RequestBody int sender, @RequestBody int receiver, @RequestBody String content) throws IOException {
		logger.info("메시지 전송");
		
		try {
			Message message = new Message();
			message.setUserSender(sender);
			message.setUserReceiver(receiver);
			message.setOwner(sender);
			message.setTime(new Date());
			message.setContent(content);
			message.setRead(false);
			
			boolean result = messageService.insertMessage(message);
			
			if (result) {
				return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
			}
			
		} catch(Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
