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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mococo.common.model.Comment;
import com.mococo.common.model.Post;
import com.mococo.common.service.CommentService;
import com.mococo.common.service.NoticeService;
import com.mococo.common.service.PostService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8080/swagger-ui.html/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/comment")

public class CommentController {

	public static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String ERROR = "error";

	@Autowired
	CommentService commentService;
	
	@Autowired
	NoticeService noticeService;
	
	@Autowired
	PostService postService;
	
	@RequestMapping(value = "/{postno}", method = RequestMethod.GET)
	@ApiOperation(value = "하나의 게시물 안에 있는 댓글들 조회")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<?> searchComment(@PathVariable String postno) throws IOException {
		logger.info("게시물별 댓글 조회");
		int post_number = Integer.parseInt(postno);
		return new ResponseEntity<List<Comment>>(commentService.findAllByPostNumber(post_number), HttpStatus.OK);

	}
	
	
	@RequestMapping(value = {"/{postno}/{userno}"}, method = RequestMethod.GET)
	@ApiOperation(value = "해당 게시물 안에 해당 유저가 좋아요 누른 댓글 리스트 조회")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<?> searchComment(@PathVariable("postno") String postno, @PathVariable("userno") String userno) throws IOException {
		logger.info("좋아요한 댓글 조회");
		int post_number = Integer.parseInt(postno);
		int user_number = Integer.parseInt(userno);
		return new ResponseEntity<List<Object>>(commentService.findAllByUserNumber(post_number,user_number), HttpStatus.OK);

	}
	 
	
	
	
	
	

	// 댓글을 쓰면 게시글 작성자에게 알림이 가야함.
	// request param 은 댓글이 게시글의 댓글인지 댓글의 대댓글인지 구분. 게시글의 댓글: parent=0, 댓글의 대댓글:
	// parent = 댓글의 comment_number
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ApiOperation(value = "댓글을 등록")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<String> insertComment(@RequestBody Comment comment,
			@RequestParam("user_number") int user_number, @RequestParam("parent") int parent,
			@RequestParam("postno") int postno) throws IOException {
		logger.info("댓글 등록");

		Date time = new Date();

		comment.setDate(time);
		try {

			comment.setUserNumber(user_number);
			comment.setPostNumber(postno);
			comment.setParent(parent);
			boolean ret = commentService.insertComment(comment);
			

			
			if (ret == false) {
				logger.info("댓글 등록 실패");
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
			
			///////////////////////////////// 알림 부분 등록- insertComment 성공시만 알림에 추가.
			//post number로 게시글 작성자 찾고 그 사용자에게 notice 보내줌, content에 post title도 넣어준다.
			
			Optional<Post> post = postService.findPostByPostNumber(postno);
			String title ="커뮤니티 알림";
			String content ="";
			
			
			content = post.get().getTitle() + "에 댓글이 달렸습니다.";
			noticeService.insertNotice(post.get().getUserNumber(),postno, title, content);

			/////////////////////////////////

			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("댓글 등록 오류");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/{commentno}", method = RequestMethod.PUT)
	@ApiOperation(value = "댓글 수정")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<String> updateComment(@RequestBody Comment comment) throws IOException {
		logger.info("댓글 수정");
		try {

			boolean ret = commentService.updateComment(comment);
			if (ret == false) {

				logger.info("댓글 수정 실패");
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("댓글 수정 오류");
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/{comment_number}", method = RequestMethod.DELETE)
	@ApiOperation(value = "댓글 삭제")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<String> deleteComment(@PathVariable String comment_number) throws IOException {
		logger.info("댓글 삭제");

		try {
			int commentno = Integer.parseInt(comment_number);
			boolean ret = commentService.deleteComment(commentno);
			if (ret == false) {
				logger.info("댓글 삭제 실패");
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("댓글 삭제 오류");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/recommend/{commentno}", method = RequestMethod.PUT)
	@ApiOperation(value = "댓글 추천 올리기")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<String> recommentComment(@PathVariable String commentno, @RequestParam int user_number)
			throws IOException {

		try {
			logger.info("댓글 추천 올리기");
			int comment_number = Integer.parseInt(commentno);
			boolean ret = commentService.recommendComment(comment_number, user_number);
			if (ret == false) {
				logger.info("댓글 추천 내리기");
				return new ResponseEntity<String>(SUCCESS, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("댓글 추천 오류");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
