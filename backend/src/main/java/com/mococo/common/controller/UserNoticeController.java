package com.mococo.common.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mococo.common.model.User;


//http://localhost:8080/swagger-ui.html/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")

public class UserNoticeController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserNoticeController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	
	@RequestMapping(value = "/notice", method = RequestMethod.GET)
	private ResponseEntity<String> searchNotice (@RequestBody User user) throws IOException {
		logger.info("알림 기록 전체 조회");
		
		return null;


	}
	
	@RequestMapping(value = "/notice/{no}", method = RequestMethod.PUT)
	private ResponseEntity<String> readNotice (@PathVariable String no) throws IOException {
		logger.info("알림 선택 시 읽음표시 변경");
		
		return null;


	}
	
	@RequestMapping(value = "/notice", method = RequestMethod.DELETE)
	private ResponseEntity<String> deleteAllNotice (@RequestBody User user) throws IOException {
		logger.info("읽은 알림 전체 삭제");
		
		return null;


	}
	
	@RequestMapping(value = "/notice/{no}", method = RequestMethod.DELETE)
	private ResponseEntity<String> deleteNotice (@PathVariable String no) throws IOException {
		logger.info("알림 하나 삭제");
		
		return null;


	}
	
	

}
