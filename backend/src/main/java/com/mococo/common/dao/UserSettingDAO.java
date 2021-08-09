package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.User;
import com.mococo.common.model.UserSetting;


@Repository
public interface UserSettingDAO extends JpaRepository<UserSetting, Integer>{

	public Optional<UserSetting> findByUserNumber(int userNumber);

	
	
	

}
