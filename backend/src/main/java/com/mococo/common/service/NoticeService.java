package com.mococo.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.NoticeDAO;
import com.mococo.common.model.Notice;

@Service
public class NoticeService {
	
	@Autowired
	NoticeDAO noticeDAO;
	
	public List<Notice> findAll(){
		List<Notice> notice = new ArrayList<>();
		noticeDAO.findAll().forEach(e -> notice.add(e));
		return notice;
	}
	
	
	
	
	
	
}
