package com.mococo.common.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mococo.common.model.UserCrop;
import com.mococo.common.model.UserCropRecord;
import com.mococo.common.service.UserCropRecordService;
import com.mococo.common.service.UserCropService;

import io.swagger.annotations.ApiOperation;


//http://localhost:8080/swagger-ui.html/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user/crop/")

public class UserCropController {
	
	@Autowired
	UserCropService userCropService;
	
	@Autowired
	UserCropRecordService userCropRecordService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserCropController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ERROR = "error";
	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ApiOperation(value = "작물 등록")
	private ResponseEntity<String> insertCrop (@RequestBody UserCrop userCrop) throws IOException {
		logger.info("작물 등록");
		
		try {
			userCrop.setPlantedDate(new Date());
			boolean result = userCropService.insertCrop(userCrop);
			
			if (result) {
				return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
			}
			
		} catch(Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	@ApiOperation(value = "작물 삭제")
	private ResponseEntity<String> deleteCrop (@RequestBody int userCropNumber) throws IOException {
		logger.info("작물 삭제");
		
		try {
			userCropService.deleteCrop(userCropNumber);
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	@ApiOperation(value = "작물 정보 수정")
	private ResponseEntity<String> updateCrop (@RequestBody UserCrop userCrop) throws IOException {
		logger.info("작물 정보 수정");
		
		try {
			boolean result = userCropService.updateCrop(userCrop);
			
			if (result) {
				return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation(value = "모든 작물 정보 조회")
	private ResponseEntity<?> searchCrop (@RequestParam int userNumber) throws IOException {
		logger.info("모든 작물 정보 조회");
		
		try {
			List<UserCrop> userCropList = userCropService.findAllByUserNumber(userNumber);
			return new ResponseEntity<>(userCropList, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/record", method = RequestMethod.POST)
	@ApiOperation(value = "작물 상태 기록")
	private ResponseEntity<String> insertCropRecord (@RequestBody UserCropRecord userCropRecord) throws IOException {
		logger.info("작물 상태 기록");
		
		try {
			userCropRecord.setRecordDate(new Date());
			boolean result = userCropRecordService.insertCropRecord(userCropRecord);
			
			if (result) {
				return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
			}
			
		} catch(Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/record", method = RequestMethod.GET)
	@ApiOperation(value = "해당 작물의 모든 상태 기록 조회")
	private ResponseEntity<?> searchAllCropRecord (@RequestParam int userCropNumber) throws IOException {
		logger.info("해당 작물의 모든 상태 기록 조회");
		
		try {
			List<UserCropRecord> userCropRecordList = userCropRecordService.findAllByUserCropNumber(userCropNumber);
			return new ResponseEntity<>(userCropRecordList, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
