package com.mococo.common.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.UserCrop;
import com.mococo.common.model.UserCropRecord;

@Repository
public interface UserCropRecordDAO extends JpaRepository<UserCropRecord, Integer> {
	
	public Optional<UserCropRecord> findByRecordNumber(int recordNumber);
	@Query(value = "select ucr.* from user_crop_record ucr where ucr.user_crop_number = :userCropNumber order by record_date desc", nativeQuery=true)
	public List<UserCropRecord> findAllByUserCropNumber(int userCropNumber);
	
	@Query(value = "select ucr.* from user_crop_record ucr where ucr.user_crop_number = :userCropNumber and SUBSTRING(ucr.record_date,6,2) = :month order by record_date desc", nativeQuery=true)
	public List<UserCropRecord> findAllByUserCropNumberAndDate(int userCropNumber, String month);
	
	public List<UserCropRecord> findByUserCropNumberAndRecordDateBetween(int userCropNumber, Date start, Date end);
}
