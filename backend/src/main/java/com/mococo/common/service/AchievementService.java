package com.mococo.common.service;

import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.AchievementConditionDAO;
import com.mococo.common.dao.AchievementDAO;
import com.mococo.common.dao.UserRecordDAO;
import com.mococo.common.model.Achievement;
import com.mococo.common.model.AchievementCondition;
import com.mococo.common.model.UserRecord;

@Service
public class AchievementService {
	@Autowired
	AchievementDAO achievementDAO;
	
	@Autowired
	AchievementConditionDAO achievementConditionDAO;
	
	@Autowired
	UserRecordDAO userRecordDAO;
	
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
	
	// 모든 업적의 필요 조건 리스트 반환
	public List<AchievementCondition> findAllCondition() {
		return achievementConditionDAO.findAll();
	}
	
	// 사용자의 모든 업적 상태 반환
	public JSONArray findAllUserAchievement(int userNumber) {
		JSONArray result = new JSONArray();
		List<Achievement> achievementList = achievementDAO.findAll();
		Optional<UserRecord> recordOpt = userRecordDAO.findByUserNumber(userNumber);
		
		for(Achievement achievement : achievementList) {
			JSONObject obj = new JSONObject();
			obj.put("achieveNumber", achievement.getAchieveNumber());
			obj.put("achieveName", achievement.getAchieveName());
			obj.put("achieveDec", achievement.getAchieveDec());
			obj.put("finish", false);
			
			if(recordOpt.isPresent()) {
				UserRecord record = recordOpt.get();
				List<AchievementCondition> conditionList = achievementConditionDAO.findAllByAchieveNumber(achievement.getAchieveNumber());
				boolean check = true;
				int count = 0;
				
				for(AchievementCondition condition : conditionList) {
					switch(condition.getTarget()) {
					case "gold":
						if(condition.getMax() <= record.getGold()) {
							count++;
						} else {
							check = false;
						}
						break;
						
					case "post_count":
						if(condition.getMax() <= record.getPostCount()) {
							count++;
						} else {
							check = false;
						}
						break;
						
					case "comment_count":
						if(condition.getMax() <= record.getCommentCount()) {
							count++;
						} else {
							check = false;
						}
						break;
						
					case "recommend_count":
						if(condition.getMax() <= record.getRecommendCount()) {
							count++;
						} else {
							check = false;
						}
						break;
						
					case "crop_finish_count":
						if(condition.getMax() <= record.getCropFinishCount()) {
							count++;
						} else {
							check = false;
						}
						break;
						
					case "crop_count":
						if(condition.getMax() <= record.getCropCount()) {
							count++;
						} else {
							check = false;
						}
						break;
						
					case "water_count":
						if(condition.getMax() <= record.getWaterCount()) {
							count++;
						} else {
							check = false;
						}
						break;
						
					default:
						break;
					}
					
					if(!check) break;
				}
				
				if(check && count > 0) obj.put("finish", true);
			}
			
			result.add(obj);
		}
		
		return result;
	}
}
