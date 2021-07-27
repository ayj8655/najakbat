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

	public Optional<UserSetting> findByUserNumber(String id) {

		Optional<UserSetting> user = userSettingDAO.findByUserNumber(id);
		user.ifPresent(selectUser -> {
			System.out.println(selectUser.getUserNumber());
		});

		return user;
	}

	public void updateById(String userNumber, UserSetting us) {

		Optional<UserSetting> updateUserSetting = userSettingDAO.findByUserNumber(userNumber);
		updateUserSetting.ifPresent(selectUser -> {
			userSettingDAO.save(selectUser);
		});

	}

}
