package com.mococo.common.model;

import java.util.Date;

import javax.persistence.*;

import lombok.*;


@Data 
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@Entity(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//이건 오토인크리먼트일때만 넣어주는거
	private int userNumber;
	
	
	private String id;
	private String password;
	private String nickname;
	private String email;
	@Temporal(TemporalType.TIMESTAMP)	//이거 써야 자동으로 시간 넣어줌
	private Date joinDate;
	private String address;
	private int gold;
	
	
	

}
