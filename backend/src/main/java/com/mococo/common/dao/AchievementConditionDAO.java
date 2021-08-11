package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mococo.common.model.Achievement;
import com.mococo.common.model.AchievementCondition;

@Repository
public interface AchievementConditionDAO extends JpaRepository<AchievementCondition, Integer> {
	List<AchievementCondition> findAllByAchieveNumber(int achieveNumber);
	
	List<AchievementCondition> findAll();
	
	Optional<AchievementCondition> findByTargetAndAchieveNumber(String target, int achieveNumber);
	
	@Transactional
	public void deleteByTargetAndAchieveNumber(String target, int achieveNumber);
}
