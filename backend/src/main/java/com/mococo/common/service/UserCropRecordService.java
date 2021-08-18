package com.mococo.common.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.UserCropRecordDAO;
import com.mococo.common.model.UserCropRecord;

@Service
public class UserCropRecordService {

	@Autowired
	UserCropRecordDAO userCropRecordDAO;

	public boolean insertCropRecord(UserCropRecord userCropRecord) {
		Optional<UserCropRecord> userCropRecordOpt = userCropRecordDAO
				.findByRecordNumber(userCropRecord.getRecordNumber());

		if (!userCropRecordOpt.isPresent()) {
			userCropRecordDAO.save(userCropRecord);
			return true;
		} else {
			return false;
		}
	}

	public List<UserCropRecord> findAllByUserCropNumber(int userCropNumber) {
		return userCropRecordDAO.findAllByUserCropNumber(userCropNumber);
	}

	public List<UserCropRecord> findAllByUserCropNumberAndDate(int userCropNumber) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int m = c.get(Calendar.MONTH);
		String month = Integer.toString(m + 1); // month는 + 1해야 맞다. 원래 라이브러리가 -1로 제공됨 1월이면 Calendar.MONTH : 0 으로 출력.
		if (month.length() == 1) {
			month = "0" + month;
		}

		return userCropRecordDAO.findAllByUserCropNumberAndDate(userCropNumber, month);
	}

	public Map<Integer, Integer> searchUserCropState3M(int userCropNumber) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		Date end = c.getTime();

		c.add(Calendar.MONTH, -3);

		Date start = c.getTime();
		List<UserCropRecord> list = userCropRecordDAO.findByUserCropNumberAndRecordDateBetween(userCropNumber, start, end);
		Map<Integer, Integer> map = new HashMap<>();
		for(UserCropRecord urc : list) {
			Integer state = urc.getState();
			if(map.containsKey(state)) {
				Integer count = map.get(state);
				map.put(state, count+1);
			}
			else {
				map.put(state, 1);
			}
		}
		long diffDay = (end.getTime() - start.getTime()) / (24*60*60*1000);
		
		Integer goodDay = (int)diffDay - list.size(); 
		map.put(0, goodDay);
		
		return map;
	}
}
