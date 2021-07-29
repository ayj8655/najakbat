package com.mococo.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.UserDAO;
import com.mococo.common.model.User;


@Service
public class UserService {

	@Autowired
	UserDAO userDAO;

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
		if(ret.isPresent()) {
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
		if(!updateUser.isPresent()) {
			return false;
		}
		userDAO.save(user);	
		return true;
	}

	public User login(Map<String, String> map) {
		
		Optional<User> loginUser = userDAO.findByIdAndPassword(map.get("userId"),map.get("userPwd"));
		if(!loginUser.isPresent()) {
			return null;
		}
		
		return loginUser.get();
	}
	
	/*
	public void updateById(String userId, User user) {
		
		Optional<User> updateUser = userDAO.findById(userId);
		
		updateUser.ifPresent(selectUser -> {
			
			selectUser.setNickname(user.getNickname());
			selectUser.setJoinDate(user.getJoinDate());
			selectUser.setAddress(user.getAddress());
			selectUser.setPassword(user.getPassword());
			userDAO.save(selectUser);	
			
		});	
		
		
	}*/

}
