package com.mococo.common.batch;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mococo.common.model.UserCrop;
import com.mococo.common.model.UserSetting;
import com.mococo.common.service.CropService;
import com.mococo.common.service.UserCropService;
import com.mococo.common.service.UserSettingService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class UserCropWaterSchedule {

	@Autowired
	UserCropService usercropService;

	@Autowired
	UserSettingService usersettingService;

	// 물주기 관리에 대한 부분
	@Scheduled(cron = "0 0 0 * * *") // 매일 자정에 한번 실행
	// @Scheduled(cron = "0 */1 * * * *") // 1분마다 실행
	public void taskOneDay() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowTime = sdf.format(new Date());

		// user crop에서 수확이 끝나지 않은 작물 리스트 받기
		List<UserCrop> usercrops = usercropService.findByFinishFalse();
		Calendar cal = Calendar.getInstance();

		System.out.println("자정실행");

		for (UserCrop usercrop : usercrops) {
			cal.setTime(usercrop.getNeedDate());

			// 물줘야하는 마지막날의 다음날이 된 경우
			cal.add(Calendar.DATE, 1);
			String needTime = sdf.format(cal.getTime());

			// case: 물을 기한내에 주었고, 물 기한이 다된 경우 -> water true -> false로 만든다. + (need date를 + 물
			// 주는 주기)
			if (usercrop.isWater() && nowTime.equals(needTime)) {
				System.out.println("기한내물줘서 변경함 + watercycle");
				usercrop.setWater(false);
				cal.setTime(usercrop.getNeedDate());

				cal.add(Calendar.DATE, usercrop.getWaterCycle());
				usercrop.setNeedDate(cal.getTime());
				usercropService.updateCrop(usercrop);

			}

			// case: 물을 기한내에 주지 못하고 물기한이 다된경우 -> water은 false 그대로 need date를 + 1
			else if (!usercrop.isWater() && nowTime.equals(needTime)) {
				System.out.println("기한내물못줌 하루늘림");

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
	@Scheduled(cron = "0 0 * * * *") // 매시각 실행
	// @Scheduled(cron = "0 */1 * * * *") // 1분마다 실행
	public void taskEveryHour() throws Exception {
		Calendar cal = Calendar.getInstance();
		System.out.println("매시각실행 - 사용자마다 알림설정해놓은 시각에 알림 가도록한다.");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String nowTime = sdf.format(new Date()); // 현재 년월일 정보 string으로
		int nowH = cal.get(Calendar.HOUR); // 현재 시각

		// user crop에서 수확이 끝나지 않은 작물 리스트 받기
		List<UserCrop> usercrops = usercropService.findByFinishFalse();
		for (UserCrop usercrop : usercrops) {
			Optional<UserSetting> usersetting = usersettingService.findByUserNumber(usercrop.getUserNumber());

			cal.setTime(usercrop.getNeedDate());
			String needTime = sdf.format(cal.getTime());
			System.out.println(usercrop.getUserNumber());
			// 물을 주지 않았고 현재시간이 세팅한 시간인 사람에게 물줘야하는 날에 알림을 보내줌
			if (!usercrop.isWater() && usersetting.get().getNoticeTime() == nowH && nowTime.equals(needTime)) {

				// 알림을 보낼 시에는
				// user number마다 오늘 줘야하는 작물 리스트를 만들어서 보내줘야한다.
				System.out.println(usercrop.getUserCropNumber() + "작물에 대한");
				System.out.println("알림 보내기!!");
			}

		}

	}

}
