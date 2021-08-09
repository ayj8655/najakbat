package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.Recipe;

@Repository
public interface RecipeDAO extends JpaRepository<Recipe, Integer>{

	public List<Recipe> findAll();
	public Optional<Recipe> findByRecipeNumber(int recipeNumber);
}
