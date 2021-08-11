package com.mococo.common.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.WaterRecordDAO;
import com.mococo.common.model.WaterRecord;

@Service
public class WaterRecordService {
	
	@Autowired
	WaterRecordDAO waterRecordDAO;
	
	public boolean insertWaterRecord(WaterRecord waterRecord) {
		Optional<WaterRecord> waterRecordOpt = waterRecordDAO.findByUserCropNumberAndRecordDate(waterRecord.getUserCropNumber(), waterRecord.getRecordDate());
		
		if(!waterRecordOpt.isPresent()) {
			waterRecordDAO.save(waterRecord);
			return true;
		} else {
			return false;
		}
	}
	
	public List<WaterRecord> findAllByUserCropNumber(int userCropNumber) {
		return waterRecordDAO.findAllByUserCropNumber(userCropNumber);
	}

	public List<WaterRecord> findAllByUserCropNumberAndDate(int userCropNumber) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int m = c.get(Calendar.MONTH);
		String month = Integer.toString(m+1); //month는 + 1해야 맞다. 원래 라이브러리가 -1로 제공됨 1월이면 Calendar.MONTH : 0 으로 출력.
		if(month.length()==1) {
			month = "0" + month;
		}

		return waterRecordDAO.findAllByUserCropNumberAndDate(userCropNumber, month);
	}
}
