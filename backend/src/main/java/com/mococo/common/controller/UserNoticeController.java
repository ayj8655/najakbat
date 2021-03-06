package com.mococo.common.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mococo.common.model.Notice;
import com.mococo.common.service.NoticeService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8080/swagger-ui.html/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")

public class UserNoticeController {

	private static final Logger logger = LoggerFactory.getLogger(UserNoticeController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	NoticeService noticeService;

	@RequestMapping(value = "/notice/{userNumber}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "유저번호를 입력하면 해당하는 유저의 알림을 모두 반환한다", response = List.class)
	public ResponseEntity<List<Notice>> searchNotice(@PathVariable int userNumber) throws IOException {
		logger.info("알림 기록 전체 조회");

		try {
			System.out.println("알림 기록 전체 조회 성공");
			return new ResponseEntity<List<Notice>>(noticeService.findAllByUserNumber(userNumber), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("알림 기록 전체 조회 에러");
			e.printStackTrace();
			return new ResponseEntity<List<Notice>>(noticeService.findAllByUserNumber(userNumber),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/notice/{no}", method = RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "알림 번호를 입력하면 해당하는 알림을 읽음처리하여 성공 실패 여부를 반환한다.", response = String.class)
	public ResponseEntity<String> readNotice(@PathVariable int no) throws IOException {
		logger.info("알림 선택 시 읽음표시 변경");

		try {
			boolean ret = noticeService.readNotice(no);

			if (!ret) {
				logger.info("알림 선택 시 읽음표시 실패");
				return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
			}

			System.out.println("알림 선택 시 읽음표시 성공");
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("알림 선택 시 읽음표시 에러");
			e.printStackTrace();
			return new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/notice", method = RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "해당사용자 알림 전체를 삭제한다.", response = String.class)
	public ResponseEntity<String> deleteAllNotice(@RequestParam int userNumber) throws IOException {
		logger.info("알림 전체 삭제");
		try {
			noticeService.deleteAllByUserNumber(userNumber);
			System.out.println("알림 전체 삭제 성공");
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println("알림 전체 삭제 에러");
			e.printStackTrace();
			return new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/notice/list", method = RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "선택한 알림들을 삭제한다.", response = String.class)
	public ResponseEntity<String> deleteNotice(@RequestParam List<Integer> noticeno) throws IOException {
		logger.info("선택 알림 전체 삭제");
		try {
			int ret = 0;

			ret = noticeService.deleteAllByIdIn(noticeno);
			System.out.println(ret);

			System.out.println("선택 알림  전체 삭제 성공");
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println("선택 알림  전체 삭제 에러");
			e.printStackTrace();
			return new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
