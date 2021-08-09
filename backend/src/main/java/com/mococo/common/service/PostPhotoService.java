package com.mococo.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.PostPhotoDAO;
import com.mococo.common.model.PostPhoto;

@Service
public class PostPhotoService {
	@Autowired
	PostPhotoDAO postphotoDAO;
	
	@Transactional
	public void save(PostPhoto photo) {
		
		
		postphotoDAO.save(photo);
	}
}
