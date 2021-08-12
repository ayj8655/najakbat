package com.mococo.common.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
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

import com.mococo.common.model.Achievement;
import com.mococo.common.model.AchievementCondition;
import com.mococo.common.service.AchievementService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/achievement")
public class AchievementController {

	public static final Logger logger = LoggerFactory.getLogger(AchievementController.class);
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String ERROR = "error";
	
	@Autowired
	AchievementService achievementService;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "업적 생성")
	public ResponseEntity<String> insertAchievement(@RequestParam String achieveName, @RequestParam String achieveDec) throws IOException {
		logger.info("업적 생성");

		try {
			Achievement achievement = new Achievement();
			achievement.setAchieveName(achieveName);
			achievement.setAchieveDec(achieveDec);
			boolean result = achievementService.insertAchievement(achievement);

			if (result) {
				return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "업적 리스트 조회")
	public ResponseEntity<?> searchAllAchievement() throws IOException {
		logger.info("업적 리스트 조회");

		try {
			List<Achievement> achievementList = achievementService.findAllAchievement();
			return new ResponseEntity<>(achievementList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "업적 변경")
	public ResponseEntity<String> updateAchievement(@RequestBody Achievement achievement) throws IOException {
		logger.info("업적 변경");

		try {
			boolean result = achievementService.updateAchievement(achievement);

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
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "업적 삭제")
	public ResponseEntity<String> deleteAchievement(@RequestParam int achievementNumber) throws IOException {
		logger.info("업적 삭제");

		try {
			achievementService.deleteAchievement(achievementNumber);
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/condition", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "업적 조건 생성")
	public ResponseEntity<String> insertCondition(@RequestBody AchievementCondition achievementCondition) throws IOException {
		logger.info("업적 조건 생성");

		try {
			boolean result = achievementService.insertCondition(achievementCondition);

			if (result) {
				return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/condition", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "업적 번호로 업적 조건 리스트 조회")
	public ResponseEntity<?> searchAllConditionByAchieveNumber(@RequestParam int achieveNumber) throws IOException {
		logger.info("업적 번호로 업적 조건 리스트 조회");

		try {
			List<AchievementCondition> conditionList = achievementService.findAllConditionByAchieveNumber(achieveNumber);
			return new ResponseEntity<>(conditionList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/condition/all", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "모든 업적 조건 리스트 조회")
	public ResponseEntity<?> searchAllCondition() throws IOException {
		logger.info("모든 업적 조건 리스트 조회");

		try {
			List<AchievementCondition> conditionList = achievementService.findAllCondition();
			return new ResponseEntity<>(conditionList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/condition", method = RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "업적 조건 변경")
	public ResponseEntity<String> updateCondition(@RequestBody AchievementCondition condition) throws IOException {
		logger.info("업적 조건 변경");

		try {
			boolean result = achievementService.updateCondition(condition);

			if (result) {
				return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/condition", method = RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "업적 조건 삭제")
	public ResponseEntity<String> deleteCondition(@RequestParam String target, @RequestParam int achieveNumber) throws IOException {
		logger.info("업적 조건 삭제");

		try {
			achievementService.deleteCondition(target, achieveNumber);
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "해당 유저의 모든 업적 상태 조회")
	public ResponseEntity<?> searchAllUserAchievement(@RequestParam int userNumber) throws IOException {
		logger.info("해당 유저의 모든 업적 상태 조회");

		try {
			JSONArray achievementList = achievementService.findAllUserAchievement(userNumber);
			return new ResponseEntity<>(achievementList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
