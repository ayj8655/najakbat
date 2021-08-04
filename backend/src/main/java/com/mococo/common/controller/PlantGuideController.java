package com.mococo.common.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/guide/plant")

public class PlantGuideController {
	
	@Autowired
	CropService cropService;
	
	private static final Logger logger = LoggerFactory.getLogger(PlantGuideController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ERROR = "error";
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "모든 작물 정보 검색")
	public ResponseEntity<?> searchAllPlant() throws IOException {
		logger.info("모든 작물 정보 검색");
		
		try {
			List<Crop> cropList = cropService.findAll();
			return new ResponseEntity<>(cropList, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/summary", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "모든 작물 요약 정보 검색(cropNumber, name, image)")
	public ResponseEntity<?> searchAllPlantSummary() throws IOException {
		logger.info("모든 작물 요약 정보 검색");
		
		try {
			List<Object> cropList = cropService.findAllSummary();
			return new ResponseEntity<>(cropList, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/{cropNumber}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "작물 정보 검색")
	public ResponseEntity<?> searchPlant(@PathVariable("cropNumber") String cropNumberString) throws IOException {
		logger.info("작물 정보 검색");
		
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
	
	@RequestMapping(value = "/recipe/{cropNumber}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "작물로 만들 수 있는 모든 요리 검색")
	public ResponseEntity<?> searchAllRecipeByCropNumber(@PathVariable("cropNumber") String cropNumberString) throws IOException {
		logger.info("작물로 만들 수 있는 모든 요리 검색");
		
		try {
			int cropNumber = Integer.parseInt(cropNumberString);
			List<Object> recipeList = cropService.findAllRecipeByCropNumber(cropNumber);
			
			return new ResponseEntity<>(recipeList, HttpStatus.OK);
			
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
