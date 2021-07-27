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
@RequestMapping("/comment")

public class CommentController {
	
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@RequestMapping(value = "/{postno}", method = RequestMethod.GET)
	private ResponseEntity<String> searchComment (@PathVariable String postno) throws IOException {
		logger.info("게시물별 댓글 조회");
		
		return null;


	}
	
	//여기 링크 없어서 수정해야됨
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	private ResponseEntity<String> insertComment (@RequestBody User user) throws IOException {
		logger.info("댓글 등록");
		
		return null;


	}
	
	@RequestMapping(value = "/{commentno}", method = RequestMethod.PUT)
	private ResponseEntity<String> updateComment (@PathVariable String commentno) throws IOException {
		logger.info("댓글 수정");
		
		return null;


	}
	
	
	
	@RequestMapping(value = "/{commentno}", method = RequestMethod.DELETE)
	private ResponseEntity<String> deleteComment (@PathVariable String commentno) throws IOException {
		logger.info("댓글 삭제");
		
		return null;


	}
	
	
	@RequestMapping(value = "/recommend/{commentno}", method = RequestMethod.PUT)
	private ResponseEntity<String> recommentComment (@PathVariable String commentno) throws IOException {
		logger.info("댓글 추천");
		
		return null;


	}
	
	
	
	

}
