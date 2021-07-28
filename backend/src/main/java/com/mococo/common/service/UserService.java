package com.mococo.common.service;

import java.util.ArrayList;
import java.util.List;
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
	
	public void deleteById(String userId) {
		Optional<User> deleteUser = userDAO.findById(userId);
		deleteUser.ifPresent(selectUser ->{
			userDAO.delete(selectUser);
		});
	}

	public User save(User user) {
		userDAO.save(user);
		return user;
	}

	public void updateById(String userId, User user) {
		
		Optional<User> updateUser = userDAO.findById(userId);
		
		updateUser.ifPresent(selectUser -> {
			
			selectUser.setNickname(user.getNickname());
			selectUser.setJoinDate(user.getJoinDate());
			selectUser.setAddress(user.getAddress());
			selectUser.setPassword(user.getPassword());
			userDAO.save(selectUser);	
		});

	}

}
