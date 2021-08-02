package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.UserCrop;
import com.mococo.common.model.UserCropRecord;

@Repository
public interface UserCropRecordDAO extends JpaRepository<UserCropRecord, Integer> {
	
	public Optional<UserCropRecord> findByRecordNumber(int recordNumber);
	public List<UserCropRecord> findAllByUserCropNumber(int userCropNumber);
}
