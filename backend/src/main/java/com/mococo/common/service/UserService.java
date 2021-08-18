package com.mococo.common.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mococo.common.dao.ProfilePhotoDAO;
import com.mococo.common.dao.UserDAO;
import com.mococo.common.model.Authority;
import com.mococo.common.model.LoginDto;
import com.mococo.common.model.ProfilePhoto;
import com.mococo.common.model.User;
import com.mococo.common.util.SecurityUtil;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class UserService {

	@Autowired
	UserDAO userDAO;

	@Autowired
	ProfilePhotoDAO profilephotoDAO;

	@Autowired
	private final PasswordEncoder passwordEncoder;

	@Autowired
	AmazonS3 amazonS3;

	@Value("${aws.s3.bucket}")
	private String s3bucket;

	public UserService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public User signup(User userDto) {
		// id를 기준으로 저장된 정보가 있는지 검색
		if (userDAO.findOneWithAuthoritiesById(userDto.getId()).orElse(null) != null) {
			System.out.println("이미 가입되어 있는 유저입니다.");
			throw new RuntimeException("이미 가입되어 있는 유저입니다.");
		}

		// 권한정보 생성
		Authority authority = Authority.builder().authorityName("ROLE_USER").build();

		// 유저정보 생성
		User user = User.builder().id(userDto.getId()).password(passwordEncoder.encode(userDto.getPassword()))
				.nickname(userDto.getNickname()).authorities(Collections.singleton(authority)).activated(true)
				.userName(userDto.getUserName()).joinDate(new Date()).phone(userDto.getPhone()).build();

		return userDAO.save(user);
	}

	// id를 기준으로 누구든 유저객체와 권한정보 가져옴
	// 어드민전용
	public Optional<User> getUserWithAuthorities(String id) {
		return userDAO.findOneWithAuthoritiesById(id);
	}

	// 현재 SecurityContext에 저장된 유저의 정보만
	// 유저용
	public Optional<User> getMyUserWithAuthorities() {
		return SecurityUtil.getCurrentUsername().flatMap(userDAO::findOneWithAuthoritiesById);
	}

	// api키값들은 외부로 빼면 좋을거같음
	
	@Value("${send.apiKey}")
	private String apiKey;
	
	@Value("${send.apiSecret}")
	private String apiSecret;
	
	@Value("${send.fromNumber}")
	private String fromNumber;

	public boolean sendMessage(String toNumber, String randomNumber) {

		Message coolsms = new Message(apiKey, apiSecret);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("to", toNumber);
		params.put("from", fromNumber);
		params.put("type", "SMS");
		params.put("text", "[나작밭] 인증번호 " + randomNumber + " 를 입력하세요.");
		params.put("app_version", "test app 1.2"); // application name and version

		try {
			JSONObject obj = (JSONObject) coolsms.send(params);
			System.out.println(obj.toString());
			return true;
		} catch (CoolsmsException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCode());
			return false;
		}
	}

	public List<User> findAll() {
		List<User> user = new ArrayList<>();
		userDAO.findAll().forEach(e -> user.add(e));
		return user;
	}

	public Optional<User> findById(String id) {

		Optional<User> user = userDAO.findById(id);

		user.ifPresent(selectUser -> {
			System.out.println(selectUser.getNickname());
		});

		return user;
	}

	public Optional<User> findByUserNumber(int userNumber) {

		Optional<User> user = userDAO.findByUserNumber(userNumber);

		user.ifPresent(selectUser -> {
			System.out.println(selectUser.getNickname());
		});

		return user;
	}

	public Optional<User> findByNickname(String nickName) {
		Optional<User> user = userDAO.findByNickname(nickName);

		user.ifPresent(selectUser -> {
			System.out.println(selectUser.getNickname());
		});

		return user;
	}

	// 핸드폰번호로 유저 검색
	public Optional<User> findByPhone(String phoneNumber) {

		Optional<User> user = userDAO.findByPhone(phoneNumber);

		user.ifPresent(selectUser -> {
			System.out.println(selectUser.getId());
		});

		return user;
	}

//	public void deleteById(String userId) {
//		Optional<User> deleteUser = userDAO.findById(userId);
//		
//		deleteUser.ifPresent(selectUser ->{
//			userDAO.delete(selectUser);
//		});
//	}

	public boolean deleteById(int userNumber) {
		Optional<User> ret = userDAO.findByUserNumber(userNumber);

		System.out.println("111111111111111111111111111111111");
		// delete할 post가 없는 경우
		if (!ret.isPresent()) {
			return false;
		}
		userDAO.deleteById(userNumber);
		return true;
	}

//	public User save(User user) {
//		userDAO.save(user);
//		return user;
//	}

	//모바일 로그인시 기기의 토큰값을 저장하는 메소드
	public void insertToken(LoginDto loginDto) {
		Optional<User> ret = userDAO.findById(loginDto.getId());

		ret.ifPresent(selectUser -> {
			selectUser.setToken(loginDto.getToken());
			userDAO.save(selectUser);
			System.out.println("토큰 저장 성공 ");
		});

	}

	public boolean insertUser(User user) {
		Optional<User> ret = userDAO.findByUserNumber(user.getUserNumber());
		if (ret.isPresent()) {
			return false;
		}
		userDAO.save(user);
		return true;
	}

	//유저번호를 통해 회원정보를 수정할 때 사용
	public boolean updateByUserNumber(User user) {

		Optional<User> updateUser = userDAO.findByUserNumber(user.getUserNumber());

		// update할 post가 없는 경우
		if (!updateUser.isPresent()) {
			return false;
		}

		updateUser.ifPresent(selectUser -> {

			selectUser.setNickname((user.getNickname()));
			selectUser.setPhone((user.getPhone()));
			selectUser.setAddress((user.getAddress()));

			userDAO.save(selectUser);

		});

		return true;
	}

	//비밀번호를 수정할 때 사용
	public boolean updateById(User user) {

		Optional<User> updateUser = userDAO.findById(user.getId());

		// update할 post가 없는 경우
		if (!updateUser.isPresent()) {
			return false;
		}

		updateUser.ifPresent(selectUser -> {

			selectUser.setPassword((passwordEncoder.encode(user.getPassword())));

			userDAO.save(selectUser);

		});

		return true;
	}

	public User findByUserNameAndPhone(String userName, String phone) {
		Optional<User> loginUser = userDAO.findByUserNameAndPhone(userName, phone);

		if (!loginUser.isPresent()) {
			return null;
		}

		return loginUser.get();
	}

	public boolean findByIdAndPhone(String id, String phone) {
		Optional<User> loginUser = userDAO.findByIdAndPhone(id, phone);

		if (!loginUser.isPresent()) {
			return false;
		}

		return true;
	}

	public Optional<User> insertProfilePhoto(int userNumber, MultipartFile mfile) {
		Optional<User> user = userDAO.findById(userNumber);

		if (mfile == null) {
			// TODO : 파일이 없을 땐 어떻게 해야할까.. 고민을 해보아야 할 것
			System.out.println("텅비었어....");
		}
		// 파일에 대해 DB에 저장하고 가지고 있을 것
		else {

			ProfilePhoto photo = new ProfilePhoto();
			String originalFileName = mfile.getOriginalFilename();
			if (!originalFileName.isEmpty()) {
				String sourceFileName = mfile.getOriginalFilename();
				String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

				String destinationFileName;
				destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;

				// S3 Bucket에 저장
				File file = convertMultiPartFileToFile(mfile);

				amazonS3.putObject(new PutObjectRequest(s3bucket, "profile/" + destinationFileName, file)
						.withCannedAcl(CannedAccessControlList.PublicRead));

				photo.setUser(user.get());
				photo.setOriginFile(originalFileName);
				photo.setSaveFile(destinationFileName);
				photo.setSaveFolder("profile");
				profilephotoDAO.save(photo);
				file.delete();
			}

		}
		return user;
	}

	public Optional<User> updateProfilePhoto(int userNumber, int recentPhotoNum, MultipartFile mfile) {
		Optional<User> user = userDAO.findById(userNumber);

		Optional<ProfilePhoto> recentphoto = profilephotoDAO.findById(recentPhotoNum);

		// 기존에 것을 지워야한다.

		// Bucket에서 삭제한다.
		amazonS3.deleteObject(s3bucket, recentphoto.get().getSaveFolder() + "/" + recentphoto.get().getSaveFile());

		profilephotoDAO.deleteById(recentPhotoNum);

		if (mfile == null) {
			// TODO : 파일이 없을 땐 어떻게 해야할까.. 고민을 해보아야 할 것
			System.out.println("텅비었어....");
		}
		// 파일에 대해 DB에 저장하고 가지고 있을 것
		else {

			ProfilePhoto photo = new ProfilePhoto();
			String originalFileName = mfile.getOriginalFilename();
			if (!originalFileName.isEmpty()) {
				String sourceFileName = mfile.getOriginalFilename();
				String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

				String destinationFileName;
				destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;

				// S3 Bucket에 저장
				File file = convertMultiPartFileToFile(mfile);

				amazonS3.putObject(new PutObjectRequest(s3bucket, "profile/" + destinationFileName, file)
						.withCannedAcl(CannedAccessControlList.PublicRead));

				photo.setUser(user.get());
				photo.setOriginFile(originalFileName);
				photo.setSaveFile(destinationFileName);
				photo.setSaveFolder("profile");
				profilephotoDAO.save(photo);
				file.delete();
			}

		}
		return user;
	}

	public boolean deleteProfilePhoto(int userNumber, int recentPhotoNum) {
		try {
			Optional<ProfilePhoto> recentphoto = profilephotoDAO.findById(recentPhotoNum);

			// 기존에 것을 지워야한다.

			// Bucket에서 삭제한다.
			amazonS3.deleteObject(s3bucket, recentphoto.get().getSaveFolder() + "/" + recentphoto.get().getSaveFile());

			profilephotoDAO.deleteById(recentPhotoNum);
			return true;
		} catch (AmazonServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		} catch (SdkClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return false;
	}

	private File convertMultiPartFileToFile(MultipartFile multipartFile) {
		File file = new File(multipartFile.getOriginalFilename());
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			outputStream.write(multipartFile.getBytes());
			outputStream.close();
		} catch (final IOException ex) {
			System.out.println("Error converting the multi-part file to file= " + ex.getMessage());
		}
		return file;
	}
	
	public int updateWithdraw(int userNumber) {
		return userDAO.updateWithdraw(userNumber);
	}

	/*
	 * public void updateById(String userId, User user) {
	 * 
	 * Optional<User> updateUser = userDAO.findById(userId);
	 * 
	 * updateUser.ifPresent(selectUser -> {
	 * 
	 * selectUser.setNickname(user.getNickname());
	 * selectUser.setJoinDate(user.getJoinDate());
	 * selectUser.setAddress(user.getAddress());
	 * selectUser.setPassword(user.getPassword()); userDAO.save(selectUser);
	 * 
	 * });
	 * 
	 * 
	 * }
	 */

}
