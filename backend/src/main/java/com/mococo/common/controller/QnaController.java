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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mococo.common.model.Qna;
import com.mococo.common.service.NoticeService;
import com.mococo.common.service.QnaService;

import io.swagger.annotations.ApiOperation;



@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/qna")
public class QnaController {
	public static final Logger logger = LoggerFactory.getLogger(PostController.class);
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String ERROR = "error";
	
	@Autowired
	QnaService qnaService;
	
	@Autowired
	NoticeService noticeService;
	
	// Qna등록
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ApiOperation(value = "QnA 질문하기")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<String> insertQna(
			@RequestParam(value = "userno") int userno,
			@RequestParam(value = "qnatype") String qnatype,
			@RequestParam(value = "usernickname") String usernickname,
			@RequestParam(value = "question") String question
			) throws IOException {
		
		try {
			logger.info("질문 등록");
			Qna qna = new Qna();
			qna.setDate(new Date());
			qna.setQnaType(qnatype);
			qna.setQuestion(question);
			qna.setUserNickname(usernickname);
			qna.setUserNumber(userno);
			Qna ret = qnaService.insertQna(qna);

			
			if (ret == null) {
				logger.info("질문 등록 실패");
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("질문 등록 오류");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	// Qna삭제
	@RequestMapping(value = "/{qnano}", method = RequestMethod.DELETE)
	@ApiOperation(value = "질문 삭제")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<String> deletePost(@PathVariable String qnano) throws IOException {

		try {
			logger.info("질문 삭제");
			int qna_number = Integer.parseInt(qnano);
			boolean ret = qnaService.deletePost(qna_number);
			if (ret == false) {
				logger.info("질문 삭제 실패");
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("질문 삭제 오류");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Qna 답변달기
	@RequestMapping(value = "/{qnano}", method = RequestMethod.PUT)
	@ApiOperation(value = "답변달기")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<String> answerQna(@RequestParam(value= "qnano") String qnano,
											@RequestParam(value= "answer") String answer) throws IOException {

		try {
			logger.info("답변달기");
			boolean ret = qnaService.answerQna(qnano,answer);
			if (ret == false) {

				logger.info("답변달기  실패");
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
			
			///////////////답변 달리기 성공하면 질문 한 사람에게 알림
			Optional<Qna> qna = qnaService.findById(Integer.parseInt(qnano));
			
			noticeService.insertNotice(qna.get().getUserNumber(), Integer.parseInt(qnano), "답변 알림", qna.get().getQnaType()+"에 대한 답변이 등록되었습니다."); // 알림 받을 사람번호, qna번호, 알림종류 답변, 답변내용
			///////////////

			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("답변달기 오류");
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 유저 본인의 qna 리스트 확인하기
	
	// 인피니티 스크롤로 구현
	// limit =0 일때 첫번째 post부터 3개 보내줌. limit = 1일 때 4번째부터 post를 3개 보내줌 - 최근 글이 위로 오게
	// 보낸다.
	@RequestMapping(value = "/user/{userno}", method = RequestMethod.GET)
	@ApiOperation(value = "사용자 별 질문 인피니티 스크롤")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<?> searchQnaUser(@RequestParam String limit, @PathVariable String userno)
			throws IOException {
		logger.info("사용자 qna 조회");
		try {
			int user_number = Integer.parseInt(userno);
			int limit_number = Integer.parseInt(limit);
			return new ResponseEntity<List<Object>>(qnaService.findQnaUser(user_number, limit_number), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("사용자 qna 조회 에러");
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	
	
	// 전체 qna 리스트 확인하기
	
	// 통으로 보내주기. 
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ApiOperation(value = "전체 질문 리스트 전체")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<?> searchQnaUser()
			throws IOException {
		logger.info("전체 qna 조회");
		try {

			return new ResponseEntity<List<Qna>>(qnaService.findAllByOrderByDateDesc(), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("사용자 qna 조회 에러");
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	
	

	
}
