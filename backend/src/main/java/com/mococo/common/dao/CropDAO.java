package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.Crop;

@Repository
public interface CropDAO extends JpaRepository<Crop, Integer>{
	
	public List<Crop> findAll();
	public Optional<Crop> findByCropNumber(int cropNumber);
}
