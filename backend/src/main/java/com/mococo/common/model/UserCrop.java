package com.mococo.common.model;

import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
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
@Entity(name="userCrop")
public class UserCrop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userCropNumber;
	private int userNumber;
	private int cropNumber;
	private String cropNickname;// 사용자가 정한 별칭
	private String description;	// 사용자가 적는 설명
	@Temporal(TemporalType.TIMESTAMP)
	private Date plantedDate;	// 심은날
	@Temporal(TemporalType.TIMESTAMP)
	private Date targetDate;	// 수확 예정일 (db에서 계산한거)
	@Temporal(TemporalType.TIMESTAMP)
	private Date realDate;		// 실제 수확일 (완전 수확한 날 기록함)
	@Temporal(TemporalType.TIMESTAMP)
	private Date needDate;		// 돌아오는 물줘야하는 날짜
	private boolean finish;		// 수확이 완전끝났는지 여부
	private int waterCycle;		// 물주는 주기
	private boolean isWater;	// 현재 물을 잘 주었는지 여부 - 물줘야하는 D-DAY만 FALSE가 됨
	
}
