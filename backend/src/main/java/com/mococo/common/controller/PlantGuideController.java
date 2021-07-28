package com.mococo.common.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mococo.common.model.Crop;
import com.mococo.common.service.CropService;

import io.swagger.annotations.ApiOperation;


//http://localhost:8080/swagger-ui.html/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/guide")

public class PlantGuideController {
	
	@Autowired
	CropService cropService;
	
	private static final Logger logger = LoggerFactory.getLogger(PlantGuideController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ERROR = "error";
	
	
	@RequestMapping(value = "/plant", method = RequestMethod.GET)
	@ApiOperation(value = "모든 작물 요약 정보 검색")
	private ResponseEntity<?> searchAllPlant() throws IOException {
		logger.info("모든 작물 요약 정보 검색");
		
		try {
			List<Crop> cropList = cropService.findAll();
			return new ResponseEntity<>(cropList, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/plant/{cropNumber}", method = RequestMethod.GET)
	@ApiOperation(value = "작물 상세 정보 검색")
	private ResponseEntity<?> searchPlant(@PathVariable("cropNumber") String cropNumberString) throws IOException {
		logger.info("작물 상세 정보 검색");
		
		try {
			int cropNumber = Integer.parseInt(cropNumberString);
			Optional<Crop> cropOpt = cropService.findByCropNumber(cropNumber);
			
			if(cropOpt.isPresent()) {
				return new ResponseEntity<>(cropOpt, HttpStatus.OK);
			}
			return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
			
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
