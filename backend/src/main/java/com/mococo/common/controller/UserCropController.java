package com.mococo.common.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mococo.common.model.User;
import com.mococo.common.model.UserCrop;
import com.mococo.common.model.UserCropDetailResponse;
import com.mococo.common.model.UserCropRecord;
import com.mococo.common.model.UserCropResponse;
import com.mococo.common.model.WaterRecord;
import com.mococo.common.service.UserCropRecordService;
import com.mococo.common.service.UserCropService;
import com.mococo.common.service.UserRecordService;
import com.mococo.common.service.WaterRecordService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	
	@Autowired
	UserRecordService userRecordService;

	private static final Logger logger = LoggerFactory.getLogger(UserCropController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ERROR = "error";

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "작물 등록")
	public ResponseEntity<String> insertCrop(@RequestParam String cropno, @RequestParam String userno, @RequestParam String cropnickname, @RequestParam String cropdesc, @ApiParam(value = "growingPeriodData (crop number가 0일때만 사용)", required = false) @RequestParam(required = false) Integer growingPeriodData, @ApiParam(value = "waterPeriodData (crop number가 0일때만 사용)", required = false) @RequestParam(required = false) Integer waterPeriodData)
			throws IOException {
		logger.info("작물 등록");

		try {
			int crop_number = Integer.parseInt(cropno);
			int user_number = Integer.parseInt(userno);
			Date now_time = new Date();
			Calendar cal = Calendar.getInstance();
			

			UserCrop userCrop = new UserCrop();
			userCrop.setCropNumber(crop_number);
			cal.add(Calendar.HOUR_OF_DAY, 9); // 9 시간 더해서 넣기
			now_time = cal.getTime();
			userCrop.setPlantedDate(cal.getTime());
			userCrop.setUserNumber(user_number);
			userCrop.setFinish(false);
			
			userCrop.setCropNickname(cropnickname);
			userCrop.setDescription(cropdesc);
			
			int growingPeriod = 0;
			int waterPeriod = 0;
			
			// db에 있는 작물일 경우
			if (crop_number != 0) {
				// target date, need_date: 물줘야하는날짜, finish=false, water_cycle, 다음 물 줘야하는날
				Optional<Object> dayInfo = userCropService.findGrowingPeriodAndWaterPeriod(crop_number);
				JSONObject jsonDayInfo = new JSONObject((Map) dayInfo.get());
				growingPeriod = (int) jsonDayInfo.get("growingPeriod");
				waterPeriod = (int) jsonDayInfo.get("waterPeriod");
			} else {
				growingPeriod = growingPeriodData == null ? 0 : growingPeriodData;
				waterPeriod = waterPeriodData == null ? 0 : waterPeriodData;
			}

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
			userRecordService.addCropCount(user_number);

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
	public ResponseEntity<String> deleteCrop(@RequestParam int userCropNumber) throws IOException {
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
	public ResponseEntity<String> updateCrop(@RequestParam int userCropNumber, @RequestParam String userCropNickname,@RequestParam String description) throws IOException {
		logger.info("작물 정보 수정");

		try {
			Optional<UserCrop> uc = userCropService.findByUserCropNumber(userCropNumber);
			
			uc.get().setCropNickname(userCropNickname);
			uc.get().setDescription(description);
			
			boolean result = userCropService.updateCrop(uc.get());

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
				
				// 끝난 작물은 패스
				if(crop.isFinish()) {
					continue;
				}
				
				UserCropResponse res = new UserCropResponse();

				res.setCropNumber(crop.getCropNumber());
				res.setUserCropNumber(crop.getUserCropNumber());
				res.setUserNumber(crop.getUserNumber());
				res.setCropNickname(crop.getCropNickname()); // 작물 별칭 추가
				
				cmpDate.setTime(crop.getTargetDate()); // 수확 날짜

				long diffSec = (cmpDate.getTimeInMillis() - getToday.getTimeInMillis()) / 1000;
				long diffDays = diffSec / (24 * 60 * 60); // 일자수 차이

				res.setRemainDate((int) diffDays); // 수확까지 남은 날짜

				cmpDate.setTime(crop.getNeedDate());
				if(crop.getUserCropPhoto()!=null) {
					res.setUserCropPhoto(crop.getUserCropPhoto());
				}
				diffSec = (cmpDate.getTimeInMillis() - getToday.getTimeInMillis()) / 1000;
				diffDays = diffSec / (24 * 60 * 60); // 물 주기 d-day

				res.setWaterDate((int) diffDays); // 물주기까지 남은날짜
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
	@ApiOperation(value = "사용자의 모든 작물 정보 조회")
	public ResponseEntity<?> searchCrop(@RequestParam int userNumber) throws IOException {
		logger.info("사용자의 모든 작물 정보 조회");

		try {
			List<UserCrop> userCropList = userCropService.findAllByUserNumber(userNumber);
			

			return new ResponseEntity<>(userCropList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/record", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "작물 상태 기록하기")
	public ResponseEntity<String> insertCropRecord(@RequestParam int userCropNumber, @RequestParam int state, @RequestParam String detail) throws IOException {
		logger.info("작물 상태 기록하기");

		try {
			UserCropRecord ucr = new UserCropRecord();
			ucr.setUserCropNumber(userCropNumber);
			ucr.setState(state);
			ucr.setRecordDate(new Date());
			ucr.setDetail(detail);
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.HOUR_OF_DAY, 9);
			Date date = cal.getTime();
			ucr.setRecordDate(date);
			boolean result = userCropRecordService.insertCropRecord(ucr);

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
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.HOUR_OF_DAY, 9);
			Date date = cal.getTime();
			WaterRecord waterRecord = new WaterRecord(userCropNumber, date);

			Optional<UserCrop> usercrop = userCropService.findByUserCropNumber(userCropNumber);
			
			usercrop.get().setWater(true);
			userCropService.updateCrop(usercrop.get());
			
			boolean result = waterRecordService.insertWaterRecord(waterRecord);
			userRecordService.addWaterCount(usercrop.get().getUserNumber());
			
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

	
	
	
	@RequestMapping(value = "/record/month", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "해당 작물의 모든 상태 기록 조회 -현재달만")
	public ResponseEntity<?> searchAllCropRecordMonth(@RequestParam int userCropNumber) throws IOException {
		logger.info("해당 작물의 모든 상태 기록 조회 - 현재달만");

		try {
			List<UserCropRecord> userCropRecordList = userCropRecordService.findAllByUserCropNumberAndDate(userCropNumber);
			return new ResponseEntity<>(userCropRecordList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/water/month", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "해당 작물에게 물 준 기록 조회 - 현재달만")
	public ResponseEntity<?> searchAllWaterRecordMonth(@RequestParam int userCropNumber) throws IOException {
		logger.info("해당 작물에게 물 준 기록 조회 - 현재달만");

		try {
			List<WaterRecord> waterRecordList = waterRecordService.findAllByUserCropNumberAndDate(userCropNumber);
			return new ResponseEntity<>(waterRecordList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "디테일 페이지에서 보여줄 작물 정보 조회")
	public ResponseEntity<?> searchDetailRecord(@RequestParam int userCropNumber) throws IOException {
		logger.info("디테일 페이지에서 보여줄 작물 정보 조회");

		try {
			UserCropDetailResponse urdr = new UserCropDetailResponse();
			
			Calendar getToday = Calendar.getInstance();
			getToday.setTime(new Date()); // 금일 날짜

			Calendar cmpDate = Calendar.getInstance();
			
			// 1: user crop number로 조회해오기. - usercrop, usercropresponse
			Optional<UserCrop> uc = userCropService.findByUserCropNumber(userCropNumber);
			UserCrop usercrop = uc.get();
			urdr.setUserNumber(uc.get().getUserNumber());
			
			urdr.setCropNumber(uc.get().getCropNumber());
			
			urdr.setUserCropNumber(uc.get().getUserCropNumber());
			
			urdr.setCropNickname(uc.get().getCropNickname()); // 별명 set
			
			urdr.setDescription(uc.get().getDescription()); // 설명 set
			
			urdr.setPlantedDate(uc.get().getPlantedDate()); // 등록일 set
			
			urdr.setTargetDate(uc.get().getTargetDate());  //목표 수확날짜 set
			
			cmpDate.setTime(uc.get().getTargetDate()); // 수확 날짜

			long diffSec = (cmpDate.getTimeInMillis() - getToday.getTimeInMillis()) / 1000;
			long diffDays = diffSec / (24 * 60 * 60); // 일자수 차이

			urdr.setRemainDate((int) diffDays); // 수확까지 남은 날짜 set

			cmpDate.setTime(uc.get().getNeedDate());

			diffSec = (cmpDate.getTimeInMillis() - getToday.getTimeInMillis()) / 1000;
			diffDays = diffSec / (24 * 60 * 60); // 물 주기 d-day

			urdr.setWaterDate((int) diffDays); // 물주기까지 몇일 set
			urdr.setWater(uc.get().isWater());  //물준 여부 set
			
			if(usercrop.getUserCropPhoto()!=null) {
				urdr.setUserCropPhoto(usercrop.getUserCropPhoto());
			}
			
			return new ResponseEntity<>(urdr, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	// 작물 완전 수확
	@RequestMapping(value = "/fullharvest", method = RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "완전 수확")
	public ResponseEntity<String> fullHarvestCrop(@RequestParam int userCropNumber, @RequestParam String price) throws IOException {
		logger.info("완전 수확 - 작물 리스트에서 뺀다");

		try {
			Optional<UserCrop> uc = userCropService.findByUserCropNumber(userCropNumber);
			
			uc.get().setFinish(true); 		  // 수확 완료
			uc.get().setRealDate(new Date()); // 현재 날로 실제 수확날짜 적기
			userRecordService.addCropFinishCount(uc.get().getUserNumber()); // 유저 기록에 반영
			int gold = Integer.parseInt(price);
			userRecordService.addGold(uc.get().getUserNumber(), gold);  							      // 돈 계산하기 (수확할 때 사용자한테서 정보 더 가져와야할거같기도)
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			
		
		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 작물 임시 수확
	@RequestMapping(value = "/tempharvest", method = RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "임시 수확 - 작물 그대로 유지")
	public ResponseEntity<String> tempHarvestCrop(@RequestParam int userCropNumber, @RequestParam String price) throws IOException {
		logger.info("임시 수확 - 작물 그대로 유지");

		try {
			Optional<UserCrop> uc = userCropService.findByUserCropNumber(userCropNumber);
			
			Calendar cal = Calendar.getInstance();
			Date now_time = new Date();
			
			uc.get().setPlantedDate(now_time); // 현재 날로 심은날  적기
			
			// target date, need_date: 물줘야하는날짜, finish=false, water_cycle, 다음 물 줘야하는날
			Optional<Object> dayInfo = userCropService.findGrowingPeriodAndWaterPeriod(uc.get().getCropNumber()); // 해당 crop의 정보 가져오기
			JSONObject jsonDayInfo = new JSONObject((Map) dayInfo.get());
			int growingPeriod = (int) jsonDayInfo.get("growingPeriod");
			int waterPeriod = (int) jsonDayInfo.get("waterPeriod");

			cal.setTime(now_time);

			// target date는 심은날 부터 + growingPeriod
			cal.add(Calendar.DATE, growingPeriod);
			uc.get().setTargetDate(cal.getTime()); // 목표날 crop db에서 저장된 수확기간으로 가져다가 계산해서넣기

			// 다시 심은날로 초기화
			cal.add(Calendar.DATE, growingPeriod * (-1));

			// 처음 물 줘야하는 날은 심은날 부터 + waterPeriod
			cal.add(Calendar.DATE, waterPeriod);
			uc.get().setNeedDate(cal.getTime());  // 물주기날 crop db에서 저장된 물 주기로 가져다가 계산해서 넣기 

	
			userRecordService.addCropFinishCount(uc.get().getUserNumber());			      // 유저기록에 반영
													

			int gold = Integer.parseInt(price);
			userRecordService.addGold(uc.get().getUserNumber(), gold);  	  			// 돈 계산하기 (수확할 때 사용자한테서 정보 더 가져와야할거같기도)
			
			boolean result = userCropService.updateCrop(uc.get());

			if (result) {
				return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	@ApiOperation(value = "인기 작물 리스트 반환")
	public ResponseEntity<?> searchTopCrop(@RequestParam int size) throws IOException {
		logger.info("인기 작물 리스트 반환");

		try {
			List<Object> cropList = userCropService.findTopCrop(size);
			return new ResponseEntity<>(cropList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/state3m", method = RequestMethod.GET)
	@ApiOperation(value = "특정 유저작물 최근 90일 상태기록 반환")
	public ResponseEntity<?> searchUserCropState(@RequestParam int userCropNumber) throws IOException {
		logger.info("특정 유저작물 최근 90일 상태기록 반환");

		try {
			Map<Integer, Integer> stateList = userCropRecordService.searchUserCropState3M(userCropNumber);
			return new ResponseEntity<>(stateList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@RequestMapping(value = "/photo/insert", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "내 작물 사진 등록", notes = "usercrop번호를 받아 등록잘되었는지 user 리턴. 실패시 FAIL", response = User.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "usercropNumber", value = "등록하고싶은 usercropNumber", required = true) })
	public ResponseEntity<?> insertUserCropPhoto(@RequestParam(value = "usercropNumber") int usercropNumber,
			@RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
		logger.info("내 작물 사진 등록");

		try {
			Optional<UserCrop> usercrop = userCropService.insertUserCropPhoto(usercropNumber, file);

			if (usercrop.isPresent()) {
				return new ResponseEntity<>(usercrop, HttpStatus.OK);
			}
			return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {

			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/photo/update", method = RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "내 작물 사진 수정", notes = "usercrop번호를 받아 수정잘되었는지 success 리턴.", response = User.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "usercropNumber", value = "수정하고싶은 usercropNumber", required = true) })
	public ResponseEntity<?> updateUserCropPhoto(@RequestParam(value = "usercropNumber") int usercropNumber,
			@RequestParam(value = "photoNumber") int photoNumber,
			@RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
		logger.info("내 작물 사진 수정");

		try {
			Optional<UserCrop> usercrop = userCropService.updateUserCropPhoto(usercropNumber, photoNumber, file);

			if (usercrop.isPresent()) {
				return new ResponseEntity<>(usercrop, HttpStatus.OK);
			}
			return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {

			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/photo/delete", method = RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "내 작물 사진 삭제", notes = "usercrop번호를 받아 삭제잘되었는지 success 리턴.", response = User.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "usercropNumber", value = "삭제하고싶은 usercropNumber", required = true) })
	public ResponseEntity<?> deleteUserCropPhoto(@RequestParam(value = "usercropNumber") int usercropNumber,
			@RequestParam(value = "photoNumber") int photoNumber) throws IOException {
		logger.info("내 작물 사진 삭제");

		try {
			boolean ret =userCropService.deleteUserCropPhoto(usercropNumber, photoNumber);

			if (ret == true) {
				return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {

			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
	
	
}
