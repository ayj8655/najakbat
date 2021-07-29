package com.mococo.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.Material;

@Repository
public interface MaterialDAO extends JpaRepository<Material, Integer>{
	@Query("SELECT new Map(m.recipeNumber as recipeNumber, m.cropNumber as cropNumber, c.name as name) "
			+ "FROM material m LEFT JOIN crop c "
			+ "ON m.cropNumber = c.cropNumber "
			+ "WHERE m.recipeNumber = :recipeNumber ")
	public List<Object> findAllCropByRecipeNumber(int recipeNumber);
	
//	@Query("")
//	public List<Object> findAllRecipeByCropNumber();
}
