package com.mococo.common.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.CropPrice;

@Repository
public interface CropPriceDAO extends PagingAndSortingRepository<CropPrice, Integer> {
	@Query("SELECT new Map(cp.date AS date, cp.cropName AS name, cp.price AS price) "
			+ "FROM cropPrice AS cp LEFT JOIN crop AS c "
			+ "ON cp.cropName = c.name "
			+ "WHERE c.cropNumber = :cropNumber "
			+ "ORDER BY date DESC ")
	public List<Object> findByCropNumber(Pageable pageable, int cropNumber);
}
