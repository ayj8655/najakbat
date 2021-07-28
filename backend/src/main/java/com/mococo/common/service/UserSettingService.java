package com.mococo.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.UserSettingDAO;
import com.mococo.common.model.UserSetting;

@Service
public class UserSettingService {

	@Autowired
	UserSettingDAO userSettingDAO;

	public UserSetting save(UserSetting us) {
		userSettingDAO.save(us);
		return us;
	}

	public Optional<UserSetting> findByUserNumber(int id) {

		Optional<UserSetting> user = userSettingDAO.findByUserNumber(id);
		user.ifPresent(selectUser -> {
			System.out.println(selectUser.getUserNumber());
			
		});

		return user;
	}

	public void updateByUserNumber(int userNumber, UserSetting us) {

		Optional<UserSetting> updateUserSetting = userSettingDAO.findByUserNumber(userNumber);
		updateUserSetting.ifPresent(selectUser -> {
			
			selectUser.setCommentNotice(us.getCommentNotice());
			selectUser.setDarkMode(us.getDarkMode());
			selectUser.setMessageNotice(us.getMessageNotice());
			selectUser.setRecommendNotice(us.getRecommendNotice());
			selectUser.setWaterNotice(us.getWaterNotice());
			
			userSettingDAO.save(selectUser);
		});

	}

}
