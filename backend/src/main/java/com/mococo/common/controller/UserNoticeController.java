package com.mococo.common.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mococo.common.model.Notice;
import com.mococo.common.model.User;
import com.mococo.common.service.NoticeService;


//http://localhost:8080/swagger-ui.html/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")

public class UserNoticeController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserNoticeController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	
	@Autowired
	NoticeService noticeService;
	
	@RequestMapping(value = "/notice/{userNumber}", method = RequestMethod.GET)
	private ResponseEntity<List<Notice>> searchNotice (@PathVariable int userNumber) throws IOException {
		logger.info("알림 기록 전체 조회");
		
		try {
			System.out.println("알림 기록 전체 조회 성공");
			return new ResponseEntity<List<Notice>> (noticeService.findAllByUserNumber(userNumber), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("알림 기록 전체 조회 에러");
			e.printStackTrace();
			return new ResponseEntity<List<Notice>> (noticeService.findAllByUserNumber(userNumber), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		


	}
	
	@RequestMapping(value = "/notice/{no}", method = RequestMethod.PUT)
	private ResponseEntity<String> readNotice (@PathVariable int no) throws IOException {
		logger.info("알림 선택 시 읽음표시 변경");
		
		try {
			boolean ret = noticeService.readNotice(no);
			
			if(!ret) {
				logger.info("알림 선택 시 읽음표시 실패");
				return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
			}
			
			System.out.println("알림 선택 시 읽음표시 성공");
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("알림 선택 시 읽음표시 에러");
			e.printStackTrace();
			return new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	
	//삭제는 선택해서 갈거라 아래 두개가 같은거같음 어떻게 해야할지 미정 -> 맵으로 하면 될거같긴함
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
