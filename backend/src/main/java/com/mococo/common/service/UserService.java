package com.mococo.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.UserDAO;
import com.mococo.common.model.User;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class UserService {

	@Autowired
	UserDAO userDAO;

	
	//api키값들은 외부로 빼면 좋을거같음
	private String apiKey = "NCSILJGEPXL11XYN";
	private String apiSecret = "TVVIYJQU4ZZD50FRZYFHHW2TPGLXP05B";
	private String fromNumber = "01033149036";
	
	public boolean sendMessage(String toNumber, String randomNumber) {

		Message coolsms = new Message(apiKey, apiSecret);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("to", toNumber);
		params.put("from", fromNumber);
		params.put("type", "SMS");
		params.put("text", "[대충어플이름] 인증번호 "+randomNumber+" 를 입력하세용.");
		params.put("app_version", "test app 1.2"); // application name and version

		try {
			JSONObject obj = (JSONObject) coolsms.send(params);
			System.out.println(obj.toString());
			return true;
		} catch (CoolsmsException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCode());
			return false;
		}
	}
	
	
	
	
	
	public List<User> findAll() {
		List<User> user = new ArrayList<>();
		userDAO.findAll().forEach(e -> user.add(e));
		return user;
	}

	public Optional<User> findById(String id) {

		Optional<User> user = userDAO.findById(id);

		user.ifPresent(selectUser -> {
			System.out.println(selectUser.getNickname());
		});

		return user;
	}

	public Optional<User> findByUserNumber(int userNumber) {

		Optional<User> user = userDAO.findByUserNumber(userNumber);

		user.ifPresent(selectUser -> {
			System.out.println(selectUser.getNickname());
		});

		return user;
	}
	
	//핸드폰번호로 유저 검색
	public Optional<User> findByPhone(String phoneNumber) {

		Optional<User> user = userDAO.findByEmail(phoneNumber);

		user.ifPresent(selectUser -> {
			System.out.println(selectUser.getId());
		});

		return user;
	}

//	public void deleteById(String userId) {
//		Optional<User> deleteUser = userDAO.findById(userId);
//		
//		deleteUser.ifPresent(selectUser ->{
//			userDAO.delete(selectUser);
//		});
//	}

	public boolean deleteById(int userNumber) {
		Optional<User> ret = userDAO.findByUserNumber(userNumber);

		// delete할 post가 없는 경우
		if (!ret.isPresent()) {
			return false;
		}
		userDAO.deleteById(userNumber);
		return true;
	}

//	public User save(User user) {
//		userDAO.save(user);
//		return user;
//	}

	public boolean insertUser(User user) {
		Optional<User> ret = userDAO.findByUserNumber(user.getUserNumber());
		if (ret.isPresent()) {
			return false;
		}
		userDAO.save(user);
		return true;
	}

	public boolean updateById(User user) {

		Optional<User> updateUser = userDAO.findByUserNumber(user.getUserNumber());
		System.out.println(user);
		System.out.println(updateUser);

		// update할 post가 없는 경우
		if (!updateUser.isPresent()) {
			return false;
		}
		userDAO.save(user);
		return true;
	}

	public User login(Map<String, String> map) {

		Optional<User> loginUser = userDAO.findByIdAndPassword(map.get("userId"), map.get("userPwd"));
		if (!loginUser.isPresent()) {
			return null;
		}

		return loginUser.get();
	}

	/*
	 * public void updateById(String userId, User user) {
	 * 
	 * Optional<User> updateUser = userDAO.findById(userId);
	 * 
	 * updateUser.ifPresent(selectUser -> {
	 * 
	 * selectUser.setNickname(user.getNickname());
	 * selectUser.setJoinDate(user.getJoinDate());
	 * selectUser.setAddress(user.getAddress());
	 * selectUser.setPassword(user.getPassword()); userDAO.save(selectUser);
	 * 
	 * });
	 * 
	 * 
	 * }
	 */

}
