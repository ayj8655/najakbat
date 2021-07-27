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

	public Optional<User> findById(int user_number) {
		Optional<User> user = userDAO.findById(user_number);
		return user;
	}

	public void deleteById(int user_number) {
		userDAO.deleteById(user_number);
	}

	public User save(User user) {
		userDAO.save(user);
		return user;
	}

	public void updateById(int user_number, User user) {
		Optional<User> e = userDAO.findById(user_number);
		if (e.isPresent()) {
			e.get().setId(user.getId());
			e.get().setPassword(user.getPassword());
			userDAO.save(user);
		}
	}

}
