package com.mococo.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/achievement")
public class AchievementController {

	public static final Logger logger = LoggerFactory.getLogger(AchievementController.class);
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String ERROR = "error";
	
	
	
}
