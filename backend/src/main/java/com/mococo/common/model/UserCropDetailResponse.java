package com.mococo.common.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserCropDetailResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userCropNumber;
	private int userNumber;
	private int cropNumber;
	private String cropNickname; // 별칭

	private String description; // 설명

	@Temporal(TemporalType.TIMESTAMP)
	private Date plantedDate; // 등록일

	@Temporal(TemporalType.TIMESTAMP)
	private Date targetDate; // 목표수확날짜

	private int remainDate; // 수확까지 몇일
	private int waterDate; // 물주기까지 몇일
	private boolean isWater; // 물준 여부
	private UserCropPhoto userCropPhoto;
}
