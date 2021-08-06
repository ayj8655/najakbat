package com.mococo.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.CropPrice;

@Repository
public interface CropPriceDAO extends JpaRepository<CropPrice, Integer> {
	@Query("SELECT new Map(c.cropNumber as cropNumber, c.name as name, c.image as image) FROM crop c")
	public CropPrice findByCropNumber(int cropNumber);

	@Query("SELECT new Map(c.cropNumber as cropNumber, c.name as name, c.image as image) FROM crop c")
	public List<CropPrice> findMonthPriceByCropNumber(int cropNumber);
}
