package com.mococo.common.batch;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mococo.common.service.CropService;
import com.mococo.common.service.UserCropService;

@Component
public class UserCropWaterSchedule {

	@Autowired
	UserCropService usercropService;

	@Scheduled(cron = "0 0 6 * * *") //오전 6시에 실행
	public void task6() throws Exception {
		
		// 현재 날짜가 need date랑 같은날이면 water true -> false로 만들어준다.
		// true일때, false로 바꿔주고 need date를 + 물 주는 주기
		
		
		// false일 때 need date +1일
		
		
	}

}
