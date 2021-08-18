package com.mococo.common.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "UserCropPhoto")
public class UserCropPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int photoNumber;
    
    //읽기전용 - name 이고 쓰는 곳은 mappedBy
    @JsonBackReference
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_crop_number")
	private UserCrop userCrop;

	private String saveFolder; // 파일 저장 경로
	private String originFile; // 파일 원본 이름
	private String saveFile;

	public void setUserCrop(UserCrop userCrop) {
		this.userCrop = userCrop;
	}
}