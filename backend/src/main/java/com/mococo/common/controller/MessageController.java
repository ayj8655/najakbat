package com.mococo.common.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import com.mococo.common.model.Message;
import com.mococo.common.model.User;
import com.mococo.common.service.MessageService;
import com.mococo.common.service.NoticeService;
import com.mococo.common.service.UserService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8080/swagger-ui.html/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/message")
public class MessageController {
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ERROR = "error";

	@Autowired
	MessageService messageService;

	@Autowired
	UserService userService;

	@Autowired
	NoticeService noticeService;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
//	@ApiImplicitParams({
//		@ApiImplicitParam(name = "sender", value = "전송하는 유저의 userNumber", dataType = "int", paramType = "query"),
//		@ApiImplicitParam(name = "receiver", value = "전송받는 유저의 userNumber", dataType = "int", paramType = "query"),
//		@ApiImplicitParam(name = "content", value = "메시지 내용", dataType = "string", paramType = "query"),
//	})

	// 메시지 전송 시에 받은 사람에게 알림 해줘야함.
	@ApiOperation(value = "메시지 전송")
	public ResponseEntity<String> insertMessage(@RequestParam int sender, @RequestParam int receiver,
			@RequestParam String content) throws IOException {
		logger.info("메시지 전송");

		try {
			Message message = new Message();
			message.setUserSender(sender);
			message.setUserReceiver(receiver);
			message.setContent(content);
			message.setTime(new Date());
			boolean result = messageService.insertMessage(message);

			if (result) {
				///////////////////////////////// 알림 부분 등록- message 보내기 성공시만 알림에 추가.
				// 받는 사람에 대한 그 사용자에게 notice 보내줌, content에 post title도 넣어준다.
				Optional<User> user = userService.findByUserNumber(sender);
				String title = "쪽지 알림";
				String noticeContent = "";

				noticeContent = user.get().getNickname() + "님에게 쪽지가 도착했어요";
				noticeService.insertNotice(receiver,0, title, noticeContent);

				/////////////////////////////////

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
	@ApiOperation(value = "메시지 삭제")
	public ResponseEntity<String> deleteMessage(@RequestParam int messageNumber) throws IOException {
		logger.info("메시지 삭제");

		try {
			messageService.deleteMessage(messageNumber);
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "메시지 리스트 삭제")
	public ResponseEntity<String> deleteAllMessage(@RequestParam List<Integer> messageNumberList) throws IOException {
		logger.info("메시지 삭제");

		try {
			messageService.deleteAllMessage(messageNumberList);
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "메시지 읽음 처리")
	public ResponseEntity<String> readMessage(@RequestParam int messageNumber) throws IOException {
		logger.info("메시지 읽음 처리");

		try {
			boolean result = messageService.readMessage(messageNumber);

			if (result) {
				return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/sender", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "해당 유저가 보낸 모든 메시지 조회")
	public ResponseEntity<?> searchAllMessageBySender(@RequestParam int userNumber, @RequestParam int page)
			throws IOException {
		logger.info("해당 유저가 보낸 모든 메시지 조회");

		try {
			List<Object> messageList = messageService.findAllBySender(page, userNumber, userNumber);
			return new ResponseEntity<>(messageList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/receiver", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "해당 유저가 받은 모든 메시지 조회")
	public ResponseEntity<?> searchAllMessageReceiver(@RequestParam int userNumber, @RequestParam int page)
			throws IOException {
		logger.info("해당 유저가 받은 모든 메시지 조회");

		try {
			List<Object> messageList = messageService.findAllByReceiver(page, userNumber, userNumber);
			return new ResponseEntity<>(messageList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/owner", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "해당 유저가 주고 받은 모든 메시지 조회")
	public ResponseEntity<?> searchAllMessageByOwner(@RequestParam int userNumber, @RequestParam int page)
			throws IOException {
		logger.info("해당 유저가 주고 받은 모든 메시지 조회");

		try {
			List<Object> messageList = messageService.findAllByOwner(page, userNumber);
			return new ResponseEntity<>(messageList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
