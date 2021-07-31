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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mococo.common.model.Crop;
import com.mococo.common.model.Recipe;
import com.mococo.common.model.User;
import com.mococo.common.service.RecipeService;

import io.swagger.annotations.ApiOperation;


//http://localhost:8080/swagger-ui.html/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/guide/recipe")

public class RecipeGuideController {
	
	@Autowired
	RecipeService recipeService;
	
	private static final Logger logger = LoggerFactory.getLogger(RecipeGuideController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ERROR = "error";
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation(value = "모든 요리 레시피 검색")
	private ResponseEntity<?> searchAllRecipe() throws IOException {
		logger.info("모든 요리 레시피 검색");
		
		try {
			List<Recipe> recipeList = recipeService.findAll();
			return new ResponseEntity<>(recipeList, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/{recipeNumber}", method = RequestMethod.GET)
	@ApiOperation(value = "요리 레시피 검색")
	private ResponseEntity<?> searchRecipe (@PathVariable("recipeNumber") String recipeNumberString) throws IOException {
		logger.info("요리 레시피 검색");
		
		try {
			int recipeNumber = Integer.parseInt(recipeNumberString);
			Optional<Recipe> recipeOpt = recipeService.findByRecipeNumber(recipeNumber);
			
			if(recipeOpt.isPresent()) {
				return new ResponseEntity<>(recipeOpt, HttpStatus.OK);
			}
			return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
			
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/crop/{recipeNumber}", method = RequestMethod.GET)
	@ApiOperation(value = "요리 레시피에 들어가는 모든 작물 검색")
	private ResponseEntity<?> searchAllCropByRecipeNumber(@PathVariable("recipeNumber") String recipeNumberString) throws IOException {
		logger.info("요리 레시피에 들어가는 모든 작물 검색");
		
		try {
			int recipeNumber = Integer.parseInt(recipeNumberString);
			List<Object> cropList = recipeService.findAllCropByRecipeNumber(recipeNumber);
			
			return new ResponseEntity<>(cropList, HttpStatus.OK);
			
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
