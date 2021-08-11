package com.mococo.common.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.WaterRecord;

@Repository
public interface WaterRecordDAO extends JpaRepository<WaterRecord, Integer> {
	
	public List<WaterRecord> findAllByUserCropNumber(int userCropNumber);
	public Optional<WaterRecord> findByUserCropNumberAndRecordDate(int userCropNumber, Date recordDate);
	

	@Query(value = "select wr.* from water_record wr where wr.user_crop_number = :userCropNumber and SUBSTRING(wr.record_date,6,2) = :month", nativeQuery=true)
	public List<WaterRecord> findAllByUserCropNumberAndDate(int userCropNumber, String month);
}
