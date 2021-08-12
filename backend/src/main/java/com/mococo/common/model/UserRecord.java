package com.mococo.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC) 
@Entity(name="userRecord")
public class UserRecord {
	
	@Id
	@Column(name = "user_number")
	private int userNumber;
	
	private int gold;
	private int postCount;
	private int commentCount;
	private int recommendCount;
	private int cropFinishCount;
	private int cropCount;
	private int waterCount;
}
