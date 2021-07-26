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

public class RecipeGuideController {
	
	private static final Logger logger = LoggerFactory.getLogger(RecipeGuideController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	
	
	
	@RequestMapping(value = "/recipe", method = RequestMethod.GET)
	private ResponseEntity<String> searchAllRecipe (@RequestBody User user) throws IOException {
		logger.info("모든 요리 레시피 검색");
		
		return null;


	}
	
	@RequestMapping(value = "/recipe/{id}", method = RequestMethod.GET)
	private ResponseEntity<String> searchRecipe (@PathVariable String id) throws IOException {
		logger.info("모든 요리 레시피 검색");
		
		return null;


	}
	
	
	
	

}
