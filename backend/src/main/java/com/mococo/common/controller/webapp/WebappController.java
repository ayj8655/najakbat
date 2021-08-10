package com.mococo.common.controller.webapp;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mococo.common.jwt.TokenProvider;
import com.mococo.common.model.LoginDto;
import com.mococo.common.model.TokenDto;
import com.mococo.common.model.User;
import com.mococo.common.model.UserSetting;
import com.mococo.common.service.UserService;
import com.mococo.common.service.UserSettingService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/phone")
public class WebappController {
	
	private static final Logger logger = LoggerFactory.getLogger(WebappController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	public UserService userService;

	@Autowired
	UserSettingService userSettingService;// 회원가입시 유저세팅을 저장해야하기 때문에 서비스 가져왔음

	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	public WebappController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}
	
	
	// 로그인 및 토큰 발급
		@PostMapping("/authenticate")
		@ApiOperation(value = "로그인 및 인증", notes = "로그인 및 인증 토큰을 헤더 및 바디를 통해 반환", response = TokenDto.class)
		public ResponseEntity<TokenDto> authorize(LoginDto loginDto) {
			
			
			
			// id,passoword를 통해 UsernamePasswordAuthenticationToken을 생성
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					loginDto.getId(), loginDto.getPassword());

			
			// 토큰으로 authenticate 실행되면 CustomUserDetailsService에 있는 loadUserByUsername 실행
			// 그 결과값으로 authentication객체 생성되고 이를 SecurityContext에 저장
			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			
			userService.insertToken(loginDto);			
			
			// 그 인증정보를 기반으로 jwt 토큰을 생성
			String jwt = tokenProvider.createToken(authentication);
			
			// 바디에도 넣어서 리턴
			return new ResponseEntity<>(new TokenDto(jwt), HttpStatus.OK);

		}

		// 회원가입
		@PostMapping("/signup")
		@ApiOperation(value = "회원가입", notes = "유저 정보를 통해 회원가입을 진행한다. 필수 : Id, Pw, Nickname, Phone", response = String.class)
		public ResponseEntity<String> signup(User userDto) {

			try {
				User user = userService.signup(userDto);
				if (user == null) {
					logger.info("회원가입실패");
					return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
				}

			} catch (Exception e) {
				System.out.println("회원 가입 오류");
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}

			Optional<User> tempuser = userService.findById(userDto.getId());
			UserSetting us = new UserSetting(tempuser.get().getUserNumber(), 1, 1, 1, 1, 0, 6); // 유저 설정 디폴트값 초기에 6시로 알림 세팅
			userSettingService.save(us);

			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);

		}
	
	
	
	
	
	
	
	
	

}
