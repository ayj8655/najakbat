package com.mococo.common.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mococo.common.model.User;


//http://localhost:8080/swagger-ui.html/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")

public class UserProfileController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	

	
	@RequestMapping(value = "/profile/achievement", method = RequestMethod.GET)
	private ResponseEntity<String> searchAchievement (@RequestBody User user) throws IOException {
		logger.info("업적 조회");
		
		return null;


	}
	
	@RequestMapping(value = "/profile/record", method = RequestMethod.GET)
	private ResponseEntity<String> searchRecord (@RequestBody User user) throws IOException {
		logger.info("작물 기록 전체 조회");
		
		return null;


	}
	
	
	

}
