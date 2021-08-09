package com.mococo.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@Entity(name="crop")
public class Crop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "crop_number")
	private int cropNumber;
	private String name;
	private String description;
	private String cultivation;
	private int sun;
	private int water;
	private String temperature;
	private int growingPeriod;  // 평균적으로 심은 후 수확까지 키우는 일수
	private String growthDuration; // 키우기 좋은 월 마스킹
	private String image;
}
