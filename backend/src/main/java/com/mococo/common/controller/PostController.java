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
import org.springframework.web.multipart.MultipartFile;

import com.mococo.common.model.Post;
import com.mococo.common.service.PostPhotoService;
import com.mococo.common.service.PostService;
import com.mococo.common.service.UserRecordService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8080/swagger-ui.html/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/post")

public class PostController {

	public static final Logger logger = LoggerFactory.getLogger(PostController.class);
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String ERROR = "error";

	@Autowired
	PostService postService;

	@Autowired
	PostPhotoService postphotoService;
	
	@Autowired
	UserRecordService userRecordService;

	// 하나의 게시물의 내용 조회
	@RequestMapping(value = "/{no}", method = RequestMethod.GET)
	@ApiOperation(value = "하나의 게시물 내용조회")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<Optional<Post>> searchPost(@PathVariable String no) throws IOException {
		logger.info("게시물 내용 조회");
		int post_number = Integer.parseInt(no);
		Optional<Post> post = postService.findPostByPostNumber(post_number);
		post.get().setView(post.get().getView() + 1);
		postService.updatePost(post.get()); // 조회수 +1해서 업데이트

		return new ResponseEntity<Optional<Post>>(postService.findPostByPostNumber(post_number), HttpStatus.OK);
	}

	// 게시물 전체 조회

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> searchAllPost() throws IOException {
		try {

			logger.info("게시물 전체 조회");

			return new ResponseEntity<List<Post>>(postService.findAllPost(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("게시물 전체 조회 에러");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// 게시물 n개씩 조회해서 보내주는 것
	@RequestMapping(value = "/infinite", method = RequestMethod.GET)
	@ApiOperation(value = "게시글 전체 조회 인피니티 스크롤")
	public ResponseEntity<?> searchInfinitePost(@RequestParam("limit") int limit) throws IOException {
		try {

			logger.info("게시물 무한스크롤 조회");
			return new ResponseEntity<List<Object>>(postService.findInfinitePost(limit), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("게시물 무한스크롤 에러");
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// 자유1,정보2, 질문3, 나눔4

	@RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
	@ApiOperation(value = "게시물 타입별 조회")
	public ResponseEntity<?> searchPostType(@PathVariable String type) throws IOException {
		logger.info("게시물 분류 조회");
		int postType = 0;
		if (type.equals("자유")) {
			postType = 1;
		} else if (type.equals("정보")) {
			postType = 2;
		} else if (type.equals("질문")) {
			postType = 3;
		} else if (type.equals("나눔")) {
			postType = 4;
		}

		try {
			return new ResponseEntity<List<Post>>(postService.findPostType(postType), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("게시물 분류 조회 에러");
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	// 사용자가 쓴 게시글 모음 인피니티 스크롤로 변경
	// limit =0 일때 첫번째 post부터 3개 보내줌. limit = 3일 때 4번째부터 post를 3개 보내줌 - 최근 글이 위로 오게
	// 보낸다.
	@RequestMapping(value = "/user/{userno}", method = RequestMethod.GET)
	@ApiOperation(value = "사용자 별 게시글 인피니티 스크롤")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<?> searchPostUser(@RequestParam String limit, @PathVariable String userno)
			throws IOException {
		logger.info("사용자 게시물 조회");
		try {
			int user_number = Integer.parseInt(userno);
			int limit_number = Integer.parseInt(limit);
			return new ResponseEntity<List<Object>>(postService.findPostUser(user_number, limit_number), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("사용자 게시물 조회 에러");
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	// 사용자가 좋아요 누른 게시글리스트를 무한스크롤로 넘겨준다.
	@RequestMapping(value = "/userrecommend/{userno}", method = RequestMethod.GET)
	@ApiOperation(value = "좋아요 누른 게시글 인피티니 스크롤")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<?> recommendPost(@RequestParam String limit, @PathVariable String userno) throws IOException {

		logger.info("사용자 좋아요 게시물 조회");
		try {
			int user_number = Integer.parseInt(userno);
			int limit_number = Integer.parseInt(limit);
			return new ResponseEntity<List<Object>>(postService.findPostRecommend(user_number, limit_number),
					HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("사용자 좋아요 게시물 조회 에러");
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@ApiOperation(value = "게시글 업로드")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<String> insertPost(@RequestParam(value = "type") String type,
			@RequestParam(value = "content") String content, @RequestParam(value = "title") String title,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "user_nickname") String user_nickname,
			@RequestParam(value = "user_number") int user_number,
			@RequestParam(value = "image", required = false) MultipartFile[] files) throws IOException {
		Post post = new Post();
		post.setContent(content);
		int postType = 0;
		if (type.equals("자유")) {
			postType = 1;
		} else if (type.equals("정보")) {
			postType = 2;
		} else if (type.equals("질문")) {
			postType = 3;
		} else if (type.equals("나눔")) {
			postType = 4;
		}

		if (keyword != null) {
			post.setKeyword(keyword);
		}

		post.setPostType(postType);
		post.setTitle(title);
		post.setUserNickname(user_nickname);
		post.setUserNumber(user_number);
		Date time = new Date();
		post.setDate(time);

		try {
			logger.info("게시글 등록");

			Post ret = postService.insertPost(post, files);

			if (ret == null) {
				logger.info("게시글 등록 실패");
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
			
			// 게시글 수 증가
			userRecordService.addPostCount(user_number);

			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("게시글 등록 오류");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/{postno}", method = RequestMethod.DELETE)
	@ApiOperation(value = "게시글 삭제")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<String> deletePost(@PathVariable String postno) throws IOException {

		try {
			logger.info("게시글 삭제");
			int post_number = Integer.parseInt(postno);
			boolean ret = postService.deletePost(post_number);
			if (ret == false) {
				logger.info("게시글 삭제 실패");
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("게시글 삭제 오류");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{postno}", method = RequestMethod.PUT, consumes = { "multipart/form-data" })
	@ApiOperation(value = "게시글 수정")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<String> updatePost(
			@PathVariable(value = "postno") String postno,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "content") String content, @RequestParam(value = "title") String title,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "user_nickname") String user_nickname,
			@RequestParam(value = "user_number") int user_number,
			@RequestParam(value = "insertimage", required = false) MultipartFile[] files,
			@RequestParam(value = "deleteimage", required = false) List<Integer> dlist
			) throws IOException {
		Optional<Post> p = postService.findPostByPostNumber(Integer.parseInt(postno));
		Post post = p.get();
		
		post.setContent(content);
		int postType = 0;
		if (type.equals("자유")) {
			postType = 1;
		} else if (type.equals("정보")) {
			postType = 2;
		} else if (type.equals("질문")) {
			postType = 3;
		} else if (type.equals("나눔")) {
			postType = 4;
		}

		if (keyword != null) {
			post.setKeyword(keyword);
		}

		post.setPostType(postType);
		post.setTitle(title);
		post.setUserNickname(user_nickname);
		post.setUserNumber(user_number);
		Date time = new Date();
		post.setDate(time);

		try {
			logger.info("게시글 수정");


			Post ret = postService.updatePost(post, files, dlist);


			if (ret == null) {
				logger.info("게시글 수정 실패");
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
			
			// 게시글 수 증가
			userRecordService.addPostCount(user_number);

			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("게시글 수정 오류");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	
	
	// 사용자가 게시글 추천을 누르면 게시글의 좋아요가 하나 늘어나고, 몇번 유저가 몇번 게시글에 좋아요를 눌렀는지 post_recommend
	// table에 추가한다.
	@RequestMapping(value = "/recommend/{postno}", method = RequestMethod.PUT)
	@ApiOperation(value = "게시글 추천 올리기")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<String> recommendPost(@PathVariable String postno,
			@RequestParam("user_number") int user_number) throws IOException {

		try {
			int post_number = Integer.parseInt(postno);
			int ret = postService.recommendPost(post_number, user_number);
			if(ret == 1) {
				logger.info("게시글 추천 올리기");
				userRecordService.addRecommendCount(user_number, 1);
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			} else if(ret == 0){
				logger.info("게시글 추천 내리기");
				userRecordService.addRecommendCount(user_number, -1);
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			} else {
				logger.info("해당하는 게시글이 없음");
				return new ResponseEntity<String>(SUCCESS, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("게시글 추천 올리기 오류");
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/finish/{postno}", method = RequestMethod.PUT)
	@ApiOperation(value = "질문/나눔게시글 완료")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<String> finishPost(@PathVariable String postno) throws IOException {

		try {
			logger.info("질문/나눔게시글 완료");
			int post_number = Integer.parseInt(postno);
			Optional<Post> post = postService.findPostByPostNumber(post_number);
			post.get().setFinish(true);
			boolean ret = postService.updatePost(post.get());
			if (ret == false) {
				logger.info("질문/나눔게시글 완료 실패");
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("질문/나눔게시글 완료 오류");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	@ApiOperation(value = "인기 게시글 리스트 반환")
	public ResponseEntity<?> searchTopCrop(@RequestParam int size) throws IOException {
		logger.info("인기 게시글 리스트 반환");

		try {
			List<Object> postList = postService.findTopPost(size);
			return new ResponseEntity<>(postList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
