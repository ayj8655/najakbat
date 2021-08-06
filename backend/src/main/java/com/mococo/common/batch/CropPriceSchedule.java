package com.mococo.common.batch;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mococo.common.service.CropService;

@Component
public class CropPriceSchedule {

	@Autowired
	CropService cropService;

//	@Scheduled(cron = "0 0 18 * * *") // 저녁 18시에 실행
//	public void task() throws Exception {
//		System.out.println("crop price data insert : " + LocalDateTime.now());
//		cropService.insertCropPrice("2021-07-06", "2021-08-06");
//		System.out.println("crop price data insert finish : " + LocalDateTime.now());
//	}
}
