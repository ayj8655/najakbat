package com.mococo.common.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mococo.common.jwt.JwtFilter;
import com.mococo.common.jwt.TokenProvider;
import com.mococo.common.model.TokenDto;
import com.mococo.common.model.User;
import com.mococo.common.model.UserSetting;
import com.mococo.common.service.UserService;
import com.mococo.common.service.UserSettingService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8080/swagger-ui.html/

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	public UserService userService;

	@Autowired
	UserSettingService userSettingService;// 회원가입시 유저세팅을 저장해야하기 때문에 서비스 가져왔음

	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	public UserController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}

	@GetMapping("/hello")
	public String hello() {
		// userService.sendMessage("01033818655", "1234");
		return "Main";
	}

	
	//로그인 및 토큰 발급
	@PostMapping("/authenticate")
	public ResponseEntity<TokenDto> authorize(@RequestBody Map<String, String> map) {
		System.out.println(userService);

		// id,passoword를 통해 UsernamePasswordAuthenticationToken을 생성
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(map.get("id"),
				map.get("password"));

		// 토큰으로 authenticate 실행되면 CustomUserDetailsService에 있는 loadUserByUsername 실행
		// 그 결과값으로 authentication객체 생성되고 이를 SecurityContext에 저장
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 그 인증정보를 기반으로 jwt 토큰을 생성
		String jwt = tokenProvider.createToken(authentication);
		HttpHeaders httpHeaders = new HttpHeaders();
		// 헤더에도 넣고
		httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
		// 바디에도 넣어서 리턴
		return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
	}

	//회원가입
	@PostMapping("/signup")
	public ResponseEntity<User> signup1(@RequestBody User userDto) {
		System.out.println(userDto);
		System.out.println(userService);
		return ResponseEntity.ok(userService.signup(userDto));
	}

	//자기 자신의 정보 및 인증정보 
	@GetMapping("/user1")
	@PreAuthorize("hasAnyRole('USER','ADMIN')") // 두가지 권한 모두 호출가능
	public ResponseEntity<User> getMyUserInfo() {
		System.out.println(userService);
		return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
	}

	//아이디를 통해 다른사람의 정보 겟
	@GetMapping("/user1/{username}")
	@PreAuthorize("hasAnyRole('ADMIN')") // 어드민권한을 가지고있는 사람만 호출 가능
	public ResponseEntity<User> getUserInfo(@PathVariable String username) {
		System.out.println(userService);
		System.out.println(username);
		return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
	}

	// 비밀번호 찾기

	
	
	
	//여기서부터 --------------------------------------------------------------------------------
	
	// @RequestMapping(value = "/confirmId/{userId}", method = RequestMethod.GET)
	@GetMapping("/confirmId/{userId}")
	@ApiOperation(value = "아이디 입력하면 중복여부 확인후 성공 실패 반환", response = String.class)
	public ResponseEntity<String> confirmUserId(@PathVariable String userId) throws IOException {
		logger.info("아이디 중복체크");
		
		
		System.out.println(userService);
		try {
			Optional<User> user = userService.findById(userId);

			if (!user.isPresent()) {
				logger.info("id중복 없음");
				return new ResponseEntity<String>(SUCCESS, HttpStatus.NO_CONTENT);
			}

			System.out.println("id중복 있음");
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println("id중복 검사 오류");
			return new ResponseEntity<String>("error", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	// 번호 받아서 중복확인
	@RequestMapping(value = "/confirmPhone/{userPhone}", method = RequestMethod.GET)
	@ApiOperation(value = "핸드폰번호 입력하면 중복여부 확인후 성공 실패 반환", response = String.class)
	public ResponseEntity<String> confirmUserPhone(@PathVariable String userPhone) throws IOException {
		logger.info("핸드폰번호 중복체크");

		try {
			Optional<User> user = userService.findByPhone(userPhone);

			if (!user.isPresent()) {
				logger.info("핸드폰번호중복 없음");
				return new ResponseEntity<String>(SUCCESS, HttpStatus.NO_CONTENT);
			}

			System.out.println("핸드폰번호중복 있음");
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println("핸드폰번호중복 검사 오류");
			return new ResponseEntity<String>("error", HttpStatus.NOT_ACCEPTABLE);
		}

	}

	// 핸드폰번호 받고 받은 폰 번호로 아이디 찾고 찾은 아이디 전송 (핸드폰번호만 받으면됨)
	@RequestMapping(value = "/idFind", method = RequestMethod.POST)
	@ApiOperation(value = "핸드폰번호를 받아서 아이디를 찾고 성공시 id 실패시 fail 반환", response = String.class)
	public ResponseEntity<String> idFind(@RequestBody User user) throws IOException {
		logger.info("id찾기");

		String userPhone = user.getPhone();// db바뀌면 이메일이 아니라 Phone으로수정해야함

		System.out.println(userPhone);

		try {
			Optional<User> findUser = userService.findByPhone(userPhone);

			if (!findUser.isPresent()) {
				logger.info("id찾기 실패");
				return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
			}

			System.out.println("id찾기 성공");
			return new ResponseEntity<String>(findUser.get().getId(), HttpStatus.OK);

		} catch (Exception e) {
			System.out.println("id찾기 오류");
			return new ResponseEntity<String>("error", HttpStatus.NOT_ACCEPTABLE);
		}

	}

	// 핸드폰번호 받음 -> 랜덤숫자만듦 -> 메시지 보냄 -> 숫자 프론트에 보냄
	@RequestMapping(value = "/phone", method = RequestMethod.POST)
	@ApiOperation(value = "헨드폰인증 -> 번호입력후 인증번호 생성 후 메시지보내고 인증번호를 프론트로 전송", response = String.class)
	public ResponseEntity<String> phoneaCertification(@RequestBody User user) throws IOException {
		logger.info("핸드폰인증");

		String userPhone = user.getPhone();// db바뀌면 이메일이 아니라 Phone으로수정해야함

		Random rd = new Random();// 랜덤 객체 생성
		int ran = (rd.nextInt(888888) + 111111);// 111111~999999 사이 랜덤값

		String randomNumber = Integer.toString(ran);

		System.out.println(userPhone);
		System.out.println(randomNumber);

		try {// 가져온 핸드폰번호로 랜덤넘버를 메시지로 보낸다
			boolean ret = userService.sendMessage(userPhone, randomNumber);

			if (!ret) {
				logger.info("인증번호 전송 실패");
				return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
			}

			System.out.println("인증번호 전송 성공");// 랜덤넘버 프론트로 전달
			return new ResponseEntity<String>(randomNumber, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println("인증번호 전송 오류");
			return new ResponseEntity<String>("error", HttpStatus.NOT_ACCEPTABLE);
		}

	}

	
	// 권한없이 가야할듯
	//여기까지 --------------------------------------------------------------------------------
	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ApiOperation(value = "회원가입- 유저정보로 가입하고 자동으로 세팅까지 생성한다 성공실패 반환.", response = String.class)
	public ResponseEntity<String> signup(@RequestBody User user) throws IOException {
		// 우선 회원가입 하고 가입한 아이디로 유저를 찾은다음 찾은 유저에서 유저넘버를 찾고 그걸 기준으로 세팅을 만들고 그걸 새로 저장
		logger.info("회원가입");

		Date time = new Date();
		user.setJoinDate(time);

		try {
			boolean ret = userService.insertUser(user);
			if (!ret) {
				logger.info("회원가입실패");
				return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			System.out.println("회원 가입 오류");
			return new ResponseEntity<String>("error", HttpStatus.NOT_ACCEPTABLE);
		}

		Optional<User> tempuser = userService.findById(user.getId());
//		System.out.println("이게 내가 파인드바이아이디로 찾은거 " + tempuser);
		UserSetting us = new UserSetting(tempuser.get().getUserNumber(), 1, 1, 1, 1, 0);
//		System.out.println("이건 내가 저장할거" + us);
		userSettingService.save(us);

		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);

	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "유저 아이디를 받아 검색된 유저 정보 반환.", response = User.class)
	public ResponseEntity<User> searchUser(@PathVariable String userId) throws IOException {
		logger.info("회원검색");

		System.out.println(userId);
		try {
			Optional<User> user = userService.findById(userId);
			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("회원 검색 실패");
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}

	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "모든 회원정보 검색.", response = User.class)
	public List<User> searchAllUser() throws IOException {
		logger.info("전체회원검색");
		return userService.findAll();

	}

	@RequestMapping(value = "/{userNumber}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "유저번호를 입력하면 회원탈퇴 진행.", response = String.class)
	public ResponseEntity<String> withdraw(@PathVariable String userNumber) throws IOException {
		logger.info("회원 탈퇴");

		// 저거 패스에 인트말고 다른거 넣을때 처리 해줘야함 (넘버포맷익셉션)
		System.out.println(userService);
		try {
			System.out.println(userService);
			boolean ret = userService.deleteById(Integer.parseInt(userNumber));

			if (!ret) {
				logger.info("회원탈퇴 실패");
				return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
			}

			System.out.println("회원 탈퇴 성공");
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원 탈퇴 오류");
			return new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "유저정보를 입력받고 회원정보를 수정한다. 성공실패 반환.", response = String.class)
	public ResponseEntity<String> updateUser(@RequestBody User user) throws IOException {
		logger.info("회원수정");
		try {
			boolean ret = userService.updateById(user);

			if (!ret) {
				logger.info("게시글 수정 실패");
				return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
			}

			System.out.println("회원 수정 성공");
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("회원 수정 에러");
			// e.printStackTrace();
			return new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	//이건 임시
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	@ApiOperation(value = "아이디 비밀번호를 입력받아 비교후 해당하는 유저 정보 반환", response = User.class)
	public ResponseEntity<User> login(@RequestBody Map<String, String> map) {
		logger.info("login 메소드 실행");
		System.out.println(map.get("userId"));
		System.out.println(map.get("userPwd"));

		try {
			User user = userService.login(map);
			System.out.println(user);
			if (user != null) {
				System.out.println("로그인성공 세션생성");

				return new ResponseEntity<User>(user, HttpStatus.OK);
			} else {
				System.out.println("로그인실패 ");
				return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
