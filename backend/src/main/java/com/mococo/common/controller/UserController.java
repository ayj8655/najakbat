package com.mococo.common.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mococo.common.model.User;
import com.mococo.common.service.UserService;


//http://localhost:8080/swagger-ui.html/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")

public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	
	@Autowired
	UserService userService;
	
	@GetMapping("/hello")
	public String hello() {
		return "Main";
	}
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	private ResponseEntity<User> signup (@RequestBody User user) throws IOException {
		logger.info("회원가입");
		
		
		Date time = new Date();
		user.setJoin_date(time);
		
		System.out.println(user);
		return new ResponseEntity<User>(userService.save(user), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/user", method = RequestMethod.DELETE)
	private ResponseEntity<String> withdraw (@RequestBody User user) throws IOException {
		logger.info("회원탈퇴");
		
		return null;


	}
	
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	private ResponseEntity<String> updateUser (@RequestBody User user) throws IOException {
		logger.info("회원수정");
		
		return null;


	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	private ResponseEntity<String> searchUser (@RequestBody User user) throws IOException {
		logger.info("회원검색");
		
		return null;


	}
	
	
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	private ResponseEntity<User> login(@RequestBody Map<String, String> map) {
		logger.info("login 메소드 실행");
		System.out.println(map.get("userId"));
		System.out.println(map.get("userPwd"));
		
		

		return null;
	}
	
	
	
	
	
	
	
	

}
