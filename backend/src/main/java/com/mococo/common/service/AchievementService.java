package com.mococo.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.AchievementConditionDAO;
import com.mococo.common.dao.AchievementDAO;
import com.mococo.common.model.Achievement;
import com.mococo.common.model.AchievementCondition;

@Service
public class AchievementService {
	@Autowired
	AchievementDAO achievementDAO;
	
	@Autowired
	AchievementConditionDAO achievementConditionDAO;
	
	// 업적 생성
	public boolean insertAchievement(Achievement achievement) {
		achievementDAO.save(achievement);
		return true;
	}
	
	// 업적 업데이트
	public boolean updateAchievement(Achievement achievement) {
		Optional<Achievement> achievementOpt = achievementDAO.findByAchieveNumber(achievement.getAchieveNumber());
		if(!achievementOpt.isPresent()) return false;
		
		achievementDAO.save(achievement);
		return true;
	}
	
	// 업적 조건 삭제
	public void deleteAchievement(int achieveNumber) {
		achievementDAO.deleteByAchieveNumber(achieveNumber);
	}
	
	// 모든 업적 반환
	public List<Achievement> findAllAchievement() {
		return achievementDAO.findAll();
	}
	
	// 업적 조건 생성
	public boolean insertCondition(AchievementCondition condition) {
		Optional<AchievementCondition> conditionOpt = achievementConditionDAO.findByTargetAndAchieveNumber(condition.getTarget(), condition.getAchieveNumber());
		if(conditionOpt.isPresent()) return false;
		
		achievementConditionDAO.save(condition);
		return true;
	}
	
	// 업적 조건 업데이트
	public boolean updateCondition(AchievementCondition condition) {
		Optional<AchievementCondition> conditionOpt = achievementConditionDAO.findByTargetAndAchieveNumber(condition.getTarget(), condition.getAchieveNumber());
		if(!conditionOpt.isPresent()) return false;
		
		achievementConditionDAO.save(condition);
		return true;
	}
	
	// 업적 조건 삭제
	public void deleteCondition(String target, int achieveNumber) {
		achievementConditionDAO.deleteByTargetAndAchieveNumber(target, achieveNumber);
	}
	
	// 해당 업적의 필요 조건 리스트 반환
	public List<AchievementCondition> findAllConditionByAchieveNumber(int achieveNumber) {
		return achievementConditionDAO.findAllByAchieveNumber(achieveNumber);
	}
}
