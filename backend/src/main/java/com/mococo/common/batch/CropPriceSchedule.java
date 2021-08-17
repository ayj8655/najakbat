package com.mococo.common.batch;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mococo.common.service.CropService;

@Component
public class CropPriceSchedule {

	@Autowired
	CropService cropService;

	@Scheduled(cron = "0 0 22 * * *") // 저녁 22시에 실행
	public void task() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.DATE, -1);
		
		String start = format.format(startCalendar.getTime());
		String end = format.format(date);
		System.out.println("crop price data insert : " + start + "~" + end + " : " + LocalDateTime.now());
		cropService.insertCropPrice(start, end);
		System.out.println("crop price data insert finish : " + LocalDateTime.now());
	}
}
