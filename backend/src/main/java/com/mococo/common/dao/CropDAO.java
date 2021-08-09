package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.Crop;

@Repository
public interface CropDAO extends JpaRepository<Crop, Integer>{
	public List<Crop> findAll();
	
	@Query("SELECT new Map(c.cropNumber AS cropNumber, c.name AS name, c.image AS image) FROM crop AS c ORDER BY name")
	public List<Object> findAllSummary();
	
	public Optional<Crop> findByCropNumber(int cropNumber);
}
