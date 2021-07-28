package com.mococo.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.NoticeDAO;
import com.mococo.common.model.Notice;
import com.mococo.common.model.User;

@Service
public class NoticeService {

	@Autowired
	NoticeDAO noticeDAO;

//	public List<Notice> findByUserNumber(int userNumber) {
//		List<Notice> notice = new ArrayList<>();
//		noticeDAO.findAll().forEach(e -> notice.add(e));
//		return notice;
//	}

	
	public List<Notice> findAllByUserNumber(int userNumber) {
		List<Notice> notice = noticeDAO.findAllByUserNumber(userNumber);
		
		return notice;
	}
	public Optional<Notice> findByNoticeNumber(int noticeNumber) {

		Optional<Notice> notice = noticeDAO.findByNoticeNumber(noticeNumber);

		notice.ifPresent(selectUser -> {

		});

		return notice;
	}

	public boolean readNotice(int noticeNumber) {

		Optional<Notice> updateNotice = noticeDAO.findByNoticeNumber(noticeNumber);

		System.out.println(updateNotice);

		// update할 post가 없는 경우
		if (!updateNotice.isPresent()) {
			return false;
		}

		updateNotice.get().setIsRead(1);

		System.out.println(updateNotice.get());
		noticeDAO.save(updateNotice.get());//.get() 안하면 옵셔널이라서 뭔지 모르기 때문에 해줘야함

		return true;
	}

}
