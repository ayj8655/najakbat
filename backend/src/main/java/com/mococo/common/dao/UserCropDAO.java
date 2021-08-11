package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mococo.common.model.Crop;
import com.mococo.common.model.UserCrop;
import com.mococo.common.model.UserCropDetailResponse;

@Repository
public interface UserCropDAO extends JpaRepository<UserCrop, Integer>{
	
	public Optional<UserCrop> findByUserCropNumber(int userCropNumber);
	public List<UserCrop> findAllByUserNumber(int userNumber);
	@Transactional
	public void deleteByUserCropNumber(int userCropNumber);
	
	@Query(value = "SELECT new Map(c.growingPeriod as growingPeriod, c.waterPeriod as waterPeriod) "
			+ "FROM crop c "
			+ "WHERE crop_number = :no ")
	public Optional<Object> findGrowingPeriodAndWaterPeriod(int no);
	

	public List<UserCrop> findByFinishFalse();
	
	@Query(value = "SELECT c "
			+"FROM crop as c " 
			+"WHERE crop_number = :no ")
	public Optional<Crop> findNameByCropNumber(int no);
	
}
