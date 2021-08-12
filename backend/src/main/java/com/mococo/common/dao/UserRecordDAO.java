package com.mococo.common.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.UserRecord;

@Repository
public interface UserRecordDAO extends JpaRepository<UserRecord, Integer> {
	
	public Optional<UserRecord> findByUserNumber(int userNumber);
	
	@Transactional
	@Modifying
	@Query("UPDATE userRecord AS r SET r.gold = r.gold + :gold WHERE r.userNumber = :userNumber")
	public int AddGold(int userNumber, int gold);
	
	@Transactional
	@Modifying
	@Query("UPDATE userRecord AS r SET r.postCount = r.postCount + 1 WHERE r.userNumber = :userNumber")
	public int AddPostCount(int userNumber);
	
	@Transactional
	@Modifying
	@Query("UPDATE userRecord AS r SET r.commentCount = r.commentCount + 1 WHERE r.userNumber = :userNumber")
	public int AddCommentCount(int userNumber);
	
	@Transactional
	@Modifying
	@Query("UPDATE userRecord AS r SET r.recommendCount = r.recommendCount + :value WHERE r.userNumber = :userNumber")
	public int AddRecommendCount(int userNumber, int value);
	
	@Transactional
	@Modifying
	@Query("UPDATE userRecord AS r SET r.cropFinishCount = r.cropFinishCount + 1 WHERE r.userNumber = :userNumber")
	public int AddCropFinishCount(int userNumber);
	
	@Transactional
	@Modifying
	@Query("UPDATE userRecord AS r SET r.cropCount = r.cropCount + 1 WHERE r.userNumber = :userNumber")
	public int AddCropCount(int userNumber);
	
	@Transactional
	@Modifying
	@Query("UPDATE userRecord AS r SET r.waterCount = r.waterCount + 1 WHERE r.userNumber = :userNumber")
	public int AddWaterCount(int userNumber);
	
}
