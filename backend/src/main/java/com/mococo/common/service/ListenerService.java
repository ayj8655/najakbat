package com.mococo.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.UserDAO;

@Service
public class ListenerService {

	@Autowired
	UserDAO userDAO;
}
