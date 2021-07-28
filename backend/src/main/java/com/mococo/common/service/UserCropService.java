package com.mococo.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.UserCropDAO;
import com.mococo.common.model.UserCrop;

@Service
public class UserCropService {
	
	@Autowired
	UserCropDAO userCropDAO;
	
	public boolean insertCrop(UserCrop userCrop) {
		Optional<UserCrop> userCropOpt = userCropDAO.findByUserCropNumber(userCrop.getUserCropNumber());
		
		if(!userCropOpt.isPresent()) {
			userCropDAO.save(userCrop);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateCrop(UserCrop userCrop) {
		Optional<UserCrop> userCropOpt = userCropDAO.findByUserCropNumber(userCrop.getUserCropNumber());
		
		if(userCropOpt.isPresent()) {
			userCropDAO.save(userCrop);
			return true;
		} else {
			return false;
		}
	}
	
	public void deleteCrop(int userCropNumber) {
		userCropDAO.deleteByUserCropNumber(userCropNumber);
	}
	
	public List<UserCrop> findAllByUserNumber(int userNumber) {
		return userCropDAO.findAllByUserNumber(userNumber);
	}
	
}
