package com.mococo.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.MaterialDAO;
import com.mococo.common.dao.RecipeDAO;
import com.mococo.common.model.Recipe;

@Service
public class RecipeService {
	
	@Autowired
	RecipeDAO recipeDAO;
	
	@Autowired
	MaterialDAO materialDAO;

	public List<Recipe> findAll() {
		return recipeDAO.findAll();
	}
	
	public Optional<Recipe> findByRecipeNumber(int recipeNumber) {
		return recipeDAO.findByRecipeNumber(recipeNumber);
	}
	
	public List<Object> findAllCropByRecipeNumber(int recipeNumber) {
		return materialDAO.findAllCropByRecipeNumber(recipeNumber);
	}
}
