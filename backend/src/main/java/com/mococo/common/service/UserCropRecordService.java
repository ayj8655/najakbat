package com.mococo.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.UserCropRecordDAO;
import com.mococo.common.model.UserCrop;
import com.mococo.common.model.UserCropRecord;

@Service
public class UserCropRecordService {
	
	@Autowired
	UserCropRecordDAO userCropRecordDAO;
	
	public boolean insertCropRecord(UserCropRecord userCropRecord) {
		Optional<UserCropRecord> userCropRecordOpt = userCropRecordDAO.findByRecordNumber(userCropRecord.getRecordNumber());
		
		if(!userCropRecordOpt.isPresent()) {
			userCropRecordDAO.save(userCropRecord);
			return true;
		} else {
			return false;
		}
	}
	
	public List<UserCropRecord> findAllByUserCropNumber(int userCropNumber) {
		return userCropRecordDAO.findAllByUserCropNumber(userCropNumber);
	}
}
