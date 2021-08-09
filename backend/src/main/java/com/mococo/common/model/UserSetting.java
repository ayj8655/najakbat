package com.mococo.common.model;

import javax.persistence.*;

import lombok.*;


@Data 
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@Entity(name="userSetting")
public class UserSetting {
	
	@Id
	private int userNumber;
	
	
	//http://egloos.zum.com/codecrue/v/2231451
	//컬럼을 아래처럼 설정한 이유 ↑
	@Column(name="waterNotice", columnDefinition = "TINYINT", length=1)
	private int waterNotice;
	
	@Column(name="recommendNotice", columnDefinition = "TINYINT", length=1)
	private int recommendNotice;
	
	@Column(name="commentNotice", columnDefinition = "TINYINT", length=1)
	private int commentNotice;
	
	@Column(name="messageNotice", columnDefinition = "TINYINT", length=1)
	private int messageNotice;
	
	@Column(name="darkMode", columnDefinition = "TINYINT", length=1)
	private int darkMode;
	
	@Column(name="noticeTime", columnDefinition = "INT")
	private int noticeTime;
}
