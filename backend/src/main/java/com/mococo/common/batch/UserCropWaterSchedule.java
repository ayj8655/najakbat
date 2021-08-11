package com.mococo.common.batch;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mococo.common.model.Crop;
import com.mococo.common.model.UserCrop;
import com.mococo.common.model.UserSetting;
import com.mococo.common.service.CropService;
import com.mococo.common.service.NoticeService;
import com.mococo.common.service.UserCropService;
import com.mococo.common.service.UserSettingService;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class UserCropWaterSchedule {

	@Autowired
	UserCropService usercropService;

	@Autowired
	UserSettingService usersettingService;
	
	@Autowired
	NoticeService noticeService;

	// 물주기 관리에 대한 부분
	@Scheduled(cron = "0 0 0 * * *") // 매일 자정에 한번 실행
	// @Scheduled(cron = "0 */1 * * * *") // 1분마다 실행 test용
	public void taskOneDay() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowTime = sdf.format(new Date());

		// user crop에서 수확이 끝나지 않은 작물 리스트 받기
		List<UserCrop> usercrops = usercropService.findByFinishFalse();
		Calendar cal = Calendar.getInstance();

		System.out.println("자정 자동 실행");

		for (UserCrop usercrop : usercrops) {
			cal.setTime(usercrop.getNeedDate());

			// 물줘야하는 마지막날의 다음날이 된 경우
			cal.add(Calendar.DATE, 1);
			String needTime = sdf.format(cal.getTime());

			// case: 물을 기한내에 주었고, 물 기한이 다된 경우 -> water true -> false로 만든다. + (need date를 + 물
			// 주는 주기)
			if (usercrop.isWater() && nowTime.equals(needTime)) {
				
				usercrop.setWater(false);
				cal.setTime(usercrop.getNeedDate());

				cal.add(Calendar.DATE, usercrop.getWaterCycle());
				usercrop.setNeedDate(cal.getTime());
				usercropService.updateCrop(usercrop);

			}

			// case: 물을 기한내에 주지 못하고 물기한이 다된경우 -> water은 false 그대로 need date를 + 1
			else if (!usercrop.isWater() && nowTime.equals(needTime)) {
				
				cal.setTime(usercrop.getNeedDate());

				cal.add(Calendar.DATE, 1);
				usercrop.setNeedDate(cal.getTime());

			}

			else {
				// 오늘이 물줘야하는 마지막날이 아닌 경우는 아무것도 안해줘도됨.
			}

		}

	}

	// 물주기 알림에 대한 부분
	@Scheduled(cron = "0 0 0 * * *") // 매일 자정에 한번 실행
	// @Scheduled(cron = "0 0 * * * *") // 매시각 실행
	// @Scheduled(cron = "0 */1 * * * *") // 1분마다 실행 test용
	public void taskEveryHour() throws Exception {
		Calendar cal = Calendar.getInstance();
		int nowH = cal.get(Calendar.HOUR_OF_DAY); // 현재 시각 24시간 형식

		System.out.println("매시각 실행 - 사용자마다 알림설정해놓은 시각에 알림 가도록한다.");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String nowTime = sdf.format(new Date()); // 현재 년월일 정보 string으로
		// user crop에서 수확이 끝나지 않은 작물 리스트 받기
		List<UserCrop> usercrops = usercropService.findByFinishFalse();
		Map<Integer, Integer> noticeMap = new HashMap<Integer, Integer>();
		
		String title = "물주기 알림";
		String content = "농작물에 물을 주세요.";
		for (UserCrop usercrop : usercrops) {
			System.out.println("need date "+usercrop.getNeedDate());
			
			
			Optional<UserSetting> usersetting = usersettingService.findByUserNumber(usercrop.getUserNumber());

			
			String needTime = sdf.format(usercrop.getNeedDate());
			// 물을 주지 않았고 현재시간이 세팅한 시간인 사람에게 물줘야하는 날에 알림을 보내줌
			
			if (!usercrop.isWater() && usersetting.get().getNoticeTime() == nowH && nowTime.equals(needTime)) {

				// 알림을 보낼 시에는
				// user number하나만 저장하여서 보낸다.
				int userNumber = usercrop.getUserNumber();
				noticeMap.put(userNumber, 0);
				
			}

		}
		///////////////// 알림 보내는 부분
		for(Integer userno : noticeMap.keySet()) {
			noticeService.insertNotice(userno,0,title,content);
		}

	}

}
