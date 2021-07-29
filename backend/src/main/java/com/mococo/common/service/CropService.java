package com.mococo.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.CropDAO;
import com.mococo.common.model.Crop;

@Service
public class CropService {
	
	@Autowired
	CropDAO cropDAO;
	
	public List<Crop> findAll() {
		return cropDAO.findAll();
	}
	
	public List<Object> findAllSummary() {
		return cropDAO.findAllSummary();
	}
	
	public Optional<Crop> findByCropNumber(int cropNumber) {
		return cropDAO.findByCropNumber(cropNumber);
	}
}
