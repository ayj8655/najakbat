package com.mococo.common.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mococo.common.dao.UserCropDAO;
import com.mococo.common.dao.UserCropPhotoDAO;
import com.mococo.common.model.Crop;
import com.mococo.common.model.ProfilePhoto;
import com.mococo.common.model.User;
import com.mococo.common.model.UserCrop;
import com.mococo.common.model.UserCropPhoto;

@Service
public class UserCropService {

	@Autowired
	UserCropDAO userCropDAO;
	
	@Autowired
	AmazonS3 amazonS3;

	@Autowired
	UserCropPhotoDAO usercropphotoDAO;
	
	@Value("${aws.s3.bucket}")
	private String s3bucket;
	
	public Optional<UserCrop> findByUserCropNumber(int no) {
		Optional<UserCrop> userCropOpt = userCropDAO.findByUserCropNumber(no);
		return userCropOpt;
	}

	public boolean insertCrop(UserCrop userCrop) {
		Optional<UserCrop> userCropOpt = userCropDAO.findByUserCropNumber(userCrop.getUserCropNumber());

		if (!userCropOpt.isPresent()) {
			userCropDAO.save(userCrop);
			return true;
		} else {
			return false;
		}
	}

	public boolean updateCrop(UserCrop userCrop) {
		Optional<UserCrop> userCropOpt = userCropDAO.findByUserCropNumber(userCrop.getUserCropNumber());

		if (userCropOpt.isPresent()) {
			userCropDAO.save(userCrop);
			return true;
		} else {
			return false;
		}
	}

	public void deleteCrop(int userCropNumber) {
		userCropDAO.deleteByUserCropNumber(userCropNumber);
	}

	public List<UserCrop> findAllByUserNumber(int userNumber) {
		
		return userCropDAO.findAllByUserNumber(userNumber);
	}

	public Optional<Object> findGrowingPeriodAndWaterPeriod(int no) {
		return userCropDAO.findGrowingPeriodAndWaterPeriod(no);
	}
	
	public Optional<Crop> findNameByCropNumber(int no) {
		
		return userCropDAO.findNameByCropNumber(no);
	}
	

	public List<UserCrop> findByFinishFalse() {
		
		return userCropDAO.findByFinishFalse();
	}
	
	public List<Object> findTopCrop(int size) {
		return userCropDAO.findTopCrop(PageRequest.of(0, size));
	}

	public Optional<UserCrop> insertUserCropPhoto(int usercropNumber, MultipartFile mfile) {
		Optional<UserCrop> usercrop = userCropDAO.findById(usercropNumber);
		UserCrop uc = usercrop.get();
		if (mfile == null) {
			// TODO : 파일이 없을 땐 어떻게 해야할까.. 고민을 해보아야 할 것
			System.out.println("텅비었어....");
		}
		// 파일에 대해 DB에 저장하고 가지고 있을 것
		else {
			 
			UserCropPhoto photo = new UserCropPhoto();
			 
			String originalFileName = mfile.getOriginalFilename();
			 
			if (!originalFileName.isEmpty()) {
				 
				String sourceFileName = mfile.getOriginalFilename();
				 
				String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
				 

				String destinationFileName;
				 
				destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
				 

				// S3 Bucket에 저장
				File file = convertMultiPartFileToFile(mfile);
				 

				amazonS3.putObject(new PutObjectRequest(s3bucket, "usercrop/" + destinationFileName, file)
						.withCannedAcl(CannedAccessControlList.PublicRead));
				 
				
				photo.setUserCrop(uc);
				
				photo.setOriginFile(originalFileName);
				 
				photo.setSaveFile(destinationFileName);
				 
				photo.setSaveFolder("usercrop");
				 
				usercropphotoDAO.save(photo);
				 
				file.delete();
				 
			}
			 

		}
		return usercrop;
	}
	
	public Optional<UserCrop> updateUserCropPhoto(int usercropNumber, int recentPhotoNum, MultipartFile mfile) {
		Optional<UserCrop> usercrop = userCropDAO.findById(usercropNumber);

		Optional<UserCropPhoto> recentphoto = usercropphotoDAO.findById(recentPhotoNum);

		// 기존에 것을 지워야한다.

		// Bucket에서 삭제한다.
		amazonS3.deleteObject(s3bucket, recentphoto.get().getSaveFolder() + "/" + recentphoto.get().getSaveFile());

		usercropphotoDAO.deleteById(recentPhotoNum);

		if (mfile == null) {
			// TODO : 파일이 없을 땐 어떻게 해야할까.. 고민을 해보아야 할 것
			System.out.println("텅비었어....");
		}
		// 파일에 대해 DB에 저장하고 가지고 있을 것
		else {

			UserCropPhoto photo = new UserCropPhoto();
			String originalFileName = mfile.getOriginalFilename();
			if (!originalFileName.isEmpty()) {
				String sourceFileName = mfile.getOriginalFilename();
				String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

				String destinationFileName;
				destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;

				// S3 Bucket에 저장
				File file = convertMultiPartFileToFile(mfile);

				amazonS3.putObject(new PutObjectRequest(s3bucket, "usercrop/" + destinationFileName, file)
						.withCannedAcl(CannedAccessControlList.PublicRead));

				photo.setUserCrop(usercrop.get());
				
				photo.setOriginFile(originalFileName);
				 
				photo.setSaveFile(destinationFileName);
				 
				photo.setSaveFolder("usercrop");
				 
				usercropphotoDAO.save(photo);
				 
				file.delete();
			}

		}
		return usercrop;
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

	public boolean deleteUserCropPhoto(int usercropNumber, int recentPhotoNum) {
		try {
			Optional<UserCropPhoto> recentphoto = usercropphotoDAO.findById(recentPhotoNum);

			// 기존에 것을 지워야한다.

			// Bucket에서 삭제한다.
			amazonS3.deleteObject(s3bucket, recentphoto.get().getSaveFolder() + "/" + recentphoto.get().getSaveFile());

			usercropphotoDAO.deleteById(recentPhotoNum);
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


	
//	public Optional<UserCropDetailResponse> findDetail(int no) {
//		
//		return userCropDAO.findDetail(no);
//	}

}
