package com.mococo.common.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.UserRecord;

@Repository
public interface UserRecordDAO extends JpaRepository<UserRecord, Integer> {
	
	public Optional<UserRecord> findByUserNumber(int userNumber);
	
	@Query("UPDATE userRecord AS r SET r.gold = r.gold + :gold WHERE r.userNumber = :userNumber")
	public void AddGold(int userNumber, int gold);
	
	@Query("UPDATE userRecord AS r SET r.postCount = r.postCount + 1 WHERE r.userNumber = :userNumber")
	public void AddPostCount(int userNumber);
	
	@Query("UPDATE userRecord AS r SET r.commentCount = r.commentCount + 1 WHERE r.userNumber = :userNumber")
	public void AddCommentCount(int userNumber);
	
	@Query("UPDATE userRecord AS r SET r.recommendCount = r.recommendCount + 1 WHERE r.userNumber = :userNumber")
	public void AddRecommendCount(int userNumber);
	
	@Query("UPDATE userRecord AS r SET r.cropFinishCount = r.cropFinishCount + 1 WHERE r.userNumber = :userNumber")
	public void AddCropFinishCount(int userNumber);
	
	@Query("UPDATE userRecord AS r SET r.cropCount = r.cropCount + 1 WHERE r.userNumber = :userNumber")
	public void AddCropCount(int userNumber);
	
	@Query("UPDATE userRecord AS r SET r.waterCount = r.waterCount + 1 WHERE r.userNumber = :userNumber")
	public void AddWaterCount(int userNumber);
	
}
