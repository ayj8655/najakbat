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
@RequestMapping("/guide")

public class PlantGuideController {
	
	private static final Logger logger = LoggerFactory.getLogger(PlantGuideController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	
	@RequestMapping(value = "/plant", method = RequestMethod.GET)
	private ResponseEntity<String> searchAllPlant (@RequestBody User user) throws IOException {
		logger.info("모든 작물 요약 정보 검색");
		
		return null;


	}
	
	@RequestMapping(value = "/plant/{id}", method = RequestMethod.GET)
	private ResponseEntity<String> searchPlant (@PathVariable String id) throws IOException {
		logger.info("작물 상세 정보 검색");
		
		return null;


	}
	
	

}
