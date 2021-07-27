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
@RequestMapping("/post")

public class PostController {
	
	private static final Logger logger = LoggerFactory.getLogger(PostController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	
	

	@RequestMapping(value = "/{no}", method = RequestMethod.GET)
	private ResponseEntity<String> searchPost (@PathVariable String no) throws IOException {
		logger.info("게시물 내용 조회");
		
		return null;


	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	private ResponseEntity<String> searchAllPost (@RequestBody User user) throws IOException {
		logger.info("게시물 전체 조회");
		
		return null;


	}
	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	private ResponseEntity<String> insertPost (@RequestBody User user) throws IOException {
		logger.info("게시글 등록");
		
		return null;


	}
	@RequestMapping(value = "/{postno}", method = RequestMethod.DELETE)
	private ResponseEntity<String> deleteComment (@PathVariable String postno) throws IOException {
		logger.info("게시글 삭제");
		
		return null;


	}
	@RequestMapping(value = "/{postno}", method = RequestMethod.PUT)
	private ResponseEntity<String> updateComment (@PathVariable String postno) throws IOException {
		logger.info("게시글 수정");
		
		return null;


	}
	
	
	
	
	
	
	@RequestMapping(value = "/recommend/{postno}", method = RequestMethod.PUT)
	private ResponseEntity<String> recommendPost (@PathVariable String postno) throws IOException {
		logger.info("게시글 추천");
		
		return null;


	}
	
	
	
	

}
