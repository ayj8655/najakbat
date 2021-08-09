package com.mococo.common.service;

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
}
