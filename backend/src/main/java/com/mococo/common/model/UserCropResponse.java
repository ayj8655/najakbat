package com.mococo.common.model;

import java.util.Date;

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
public class UserCropResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userCropNumber;
	private int userNumber;
	private int cropNumber;
	private int remainDate;
	private int waterDate;
	private boolean isWater;
}
