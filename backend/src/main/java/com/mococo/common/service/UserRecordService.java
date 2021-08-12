package com.mococo.common.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.UserRecordDAO;
import com.mococo.common.model.UserRecord;

@Service
public class UserRecordService {
	
	@Autowired
	UserRecordDAO userRecordDAO;
	
	// 유저 기록 정보 생성
	public boolean insertUserRecord(UserRecord record) {
		Optional<UserRecord> recordOpt = userRecordDAO.findByUserNumber(record.getUserNumber());
		if(!recordOpt.isPresent()) return false;
		
		userRecordDAO.save(record);
		return true;
	}
	
	// 사용자 추정 이득 추가
	public boolean addGold(int userNumber, int gold) {
		Optional<UserRecord> recordOpt = userRecordDAO.findByUserNumber(userNumber);
		if(!recordOpt.isPresent()) return false;
		
		userRecordDAO.AddGold(userNumber, gold);
		return true;
	}
	
	// 사용자의 post count 증가
	public boolean addPostCount(int userNumber) {
		Optional<UserRecord> recordOpt = userRecordDAO.findByUserNumber(userNumber);
		if(!recordOpt.isPresent()) return false;
		
		userRecordDAO.AddPostCount(userNumber);
		return true;
	}
	
	// 사용자의 comment count 증가
	public boolean addCommentCount(int userNumber) {
		Optional<UserRecord> recordOpt = userRecordDAO.findByUserNumber(userNumber);
		if(!recordOpt.isPresent()) return false;
		
		userRecordDAO.AddCommentCount(userNumber);
		return true;
	}
	
	// 사용자의 recommend count 증가
	public boolean addRecommendCount(int userNumber) {
		Optional<UserRecord> recordOpt = userRecordDAO.findByUserNumber(userNumber);
		if(!recordOpt.isPresent()) return false;
		
		userRecordDAO.AddRecommendCount(userNumber);
		return true;
	}
	
	// 사용자의 crop finish count 증가
	public boolean addCropFinishCount(int userNumber) {
		Optional<UserRecord> recordOpt = userRecordDAO.findByUserNumber(userNumber);
		if(!recordOpt.isPresent()) return false;
		
		userRecordDAO.AddCropFinishCount(userNumber);
		return true;
	}
	
	// 사용자의 crop count 증가
	public boolean addCropCount(int userNumber) {
		Optional<UserRecord> recordOpt = userRecordDAO.findByUserNumber(userNumber);
		if(!recordOpt.isPresent()) return false;
		
		userRecordDAO.AddCropCount(userNumber);
		return true;
	}
	
	// 사용자의 water count 증가
	public boolean addWaterCount(int userNumber) {
		Optional<UserRecord> recordOpt = userRecordDAO.findByUserNumber(userNumber);
		if(!recordOpt.isPresent()) return false;
		
		userRecordDAO.AddWaterCount(userNumber);
		return true;
	}
}
