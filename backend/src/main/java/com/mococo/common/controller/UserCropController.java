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

public class UserCropController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserCropController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	
	@RequestMapping(value = "/crop", method = RequestMethod.POST)
	private ResponseEntity<String> insertCrop (@RequestBody User user) throws IOException {
		logger.info("작물등록");
		
		return null;


	}
	
	@RequestMapping(value = "/crop", method = RequestMethod.DELETE)
	private ResponseEntity<String> deleteCrop (@RequestBody User user) throws IOException {
		logger.info("작물 삭제");
		
		return null;


	}
	
	@RequestMapping(value = "/crop", method = RequestMethod.PUT)
	private ResponseEntity<String> updateCrop (@RequestBody User user) throws IOException {
		logger.info("작물정보수정");
		
		return null;


	}
	
	@RequestMapping(value = "/crop", method = RequestMethod.GET)
	private ResponseEntity<String> searchCrop (@RequestBody User user) throws IOException {
		logger.info("작물정보조회");
		
		return null;


	}
	
	

}
