package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mococo.common.model.Achievement;

@Repository
public interface AchievementDAO extends JpaRepository<Achievement, Integer> {
	
	List<Achievement> findAll();
	
	Optional<Achievement> findByAchieveNumber(int achieveNumber);
	
	@Transactional
	public void deleteByAchieveNumber(int achieveNumber);
}
