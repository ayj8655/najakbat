package com.mococo.common.batch;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.mococo.common.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

@Component
public class WeatherSchedule {
	
	@Autowired
	WeatherService weatherService;
	
	
	//아래 스케쥴 주석 풀면 각 시간마다 날씨 db 업데이트 함 그래서 일단 주석해놓음
	//날씨 api 하루 요청 제한 10000, 한번 실행시 249개
	// cron = "* * * * * *" <- 이거 앞에서부터 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-7) 순서임
	
	@Scheduled(cron = "0 0 9 * * *") //오전 9시에 실행
	public void task1() throws Exception {
		weatherService.findXY("0800");
		System.out.println("The current date (1) : " + LocalDateTime.now());
	}
	
	@Scheduled(cron = "0 0 12 * * *")	//낮 12시에 실행
	public void task2() throws Exception {
		weatherService.findXY("1100");
		System.out.println("The current date (2) : " + LocalDateTime.now());
	}
	
	@Scheduled(cron = "0 0 15 * * *")	//오후 3시에 실행
	public void task3() throws Exception {
		weatherService.findXY("1400");
		System.out.println("The current date (3) : " + LocalDateTime.now());
	}
	
	@Scheduled(cron = "0 0 18 * * *")	//저녁 18시에 실행
	public void task4() throws Exception {
		weatherService.findXY("1700");
		System.out.println("The current date (4) : " + LocalDateTime.now());
	}


}
