package com.mococo.common.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mococo.common.model.UserCrop;
import com.mococo.common.model.UserCropRecord;
import com.mococo.common.model.UserCropResponse;
import com.mococo.common.model.WaterRecord;
import com.mococo.common.service.UserCropRecordService;
import com.mococo.common.service.UserCropService;
import com.mococo.common.service.WaterRecordService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8080/swagger-ui.html/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user/crop/")

public class UserCropController {

	@Autowired
	UserCropService userCropService;

	@Autowired
	UserCropRecordService userCropRecordService;

	@Autowired
	WaterRecordService waterRecordService;

	private static final Logger logger = LoggerFactory.getLogger(UserCropController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ERROR = "error";

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "작물 등록")
	public ResponseEntity<String> insertCrop(@RequestParam String cropno, @RequestParam String userno)
			throws IOException {
		logger.info("작물 등록");

		try {
			if (cropno == "1000") {
				// db에 없는 기타 작물일 때 처리 방식 추후에 만들기
			}
			int crop_number = Integer.parseInt(cropno);
			int user_number = Integer.parseInt(userno);
			Date now_time = new Date();
			Calendar cal = Calendar.getInstance();

			UserCrop userCrop = new UserCrop();
			userCrop.setCropNumber(crop_number);
			userCrop.setPlantedDate(now_time);
			userCrop.setUserNumber(user_number);
			userCrop.setFinish(false);
			// target date, need_date: 물줘야하는날짜, finish=false, water_cycle, 다음 물 줘야하는날
			Optional<Object> dayInfo = userCropService.findGrowingPeriodAndWaterPeriod(crop_number);
			JSONObject jsonDayInfo = new JSONObject((Map) dayInfo.get());
			int growingPeriod = (int) jsonDayInfo.get("growingPeriod");
			int waterPeriod = (int) jsonDayInfo.get("waterPeriod");

			cal.setTime(now_time);

			// target date는 심은날 부터 + growingPeriod
			cal.add(Calendar.DATE, growingPeriod);
			userCrop.setTargetDate(cal.getTime());

			// 다시 심은날로 초기화
			cal.add(Calendar.DATE, growingPeriod * (-1));

			// 처음 물 줘야하는 날은 심은날 부터 + waterPeriod
			cal.add(Calendar.DATE, waterPeriod);
			userCrop.setNeedDate(cal.getTime());

			// 물주기 세팅: 심은날 + 물주기
			userCrop.setWaterCycle(waterPeriod);

			boolean result = userCropService.insertCrop(userCrop);

			if (result) {
				return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "작물 삭제")
	public ResponseEntity<String> deleteCrop(@RequestBody int userCropNumber) throws IOException {
		logger.info("작물 삭제");

		try {
			userCropService.deleteCrop(userCropNumber);
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "작물 정보 수정")
	public ResponseEntity<String> updateCrop(@RequestBody UserCrop userCrop) throws IOException {
		logger.info("작물 정보 수정");

		try {
			boolean result = userCropService.updateCrop(userCrop);

			if (result) {
				return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "내 농작물페이지에서 유저 농작물 리스트 보여주기")
	public ResponseEntity<?> searchUserCropList(@RequestParam int userNumber) throws IOException {
		logger.info("모든 작물 정보 조회");

		try {
			List<UserCrop> userCropList = userCropService.findAllByUserNumber(userNumber);
			List<UserCropResponse> resList = new ArrayList<>();
			Calendar getToday = Calendar.getInstance();
			getToday.setTime(new Date()); // 금일 날짜

			Calendar cmpDate = Calendar.getInstance();

			for (UserCrop crop : userCropList) {
				UserCropResponse res = new UserCropResponse();

				res.setCropNumber(crop.getCropNumber());
				res.setUserCropNumber(crop.getUserCropNumber());
				res.setUserNumber(crop.getUserNumber());

				cmpDate.setTime(crop.getTargetDate()); // 수확 날짜

				long diffSec = (cmpDate.getTimeInMillis() - getToday.getTimeInMillis()) / 1000;
				long diffDays = diffSec / (24 * 60 * 60); // 일자수 차이

				res.setRemainDate((int) diffDays);

				res.setWater(crop.isWater());
				resList.add(res);
			}

			return new ResponseEntity<>(resList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "모든 작물 정보 조회")
	public ResponseEntity<?> searchCrop(@RequestParam int userNumber) throws IOException {
		logger.info("모든 작물 정보 조회");

		try {
			List<UserCrop> userCropList = userCropService.findAllByUserNumber(userNumber);

			return new ResponseEntity<>(userCropList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/record", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "작물 상태 기록")
	public ResponseEntity<String> insertCropRecord(@RequestBody UserCropRecord userCropRecord) throws IOException {
		logger.info("작물 상태 기록");

		try {
			userCropRecord.setRecordDate(new Date());
			boolean result = userCropRecordService.insertCropRecord(userCropRecord);

			if (result) {
				return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/record", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "해당 작물의 모든 상태 기록 조회")
	public ResponseEntity<?> searchAllCropRecord(@RequestParam int userCropNumber) throws IOException {
		logger.info("해당 작물의 모든 상태 기록 조회");

		try {
			List<UserCropRecord> userCropRecordList = userCropRecordService.findAllByUserCropNumber(userCropNumber);
			return new ResponseEntity<>(userCropRecordList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/water", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "해당 작물에 물을 준 것으로 처리")
	public ResponseEntity<String> insertWaterRecord(@RequestParam int userCropNumber) throws IOException {
		logger.info("해당 작물에 물을 준 것으로 처리");

		try {
			WaterRecord waterRecord = new WaterRecord(userCropNumber, new Date());
			
			Optional<UserCrop> usercrop = userCropService.findByUserCropNumber(userCropNumber);
			usercrop.get().setWater(true);
			
			boolean result = waterRecordService.insertWaterRecord(waterRecord);

			if (result) {
				return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/water", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "해당 작물에게 물 준 기록 조회")
	public ResponseEntity<?> searchAllWaterRecord(@RequestParam int userCropNumber) throws IOException {
		logger.info("해당 작물에게 물 준 기록 조회");

		try {
			List<WaterRecord> waterRecordList = waterRecordService.findAllByUserCropNumber(userCropNumber);
			return new ResponseEntity<>(waterRecordList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
