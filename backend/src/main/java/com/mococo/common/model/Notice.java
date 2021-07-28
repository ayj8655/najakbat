package com.mococo.common.model;

import java.util.Date;

import javax.persistence.*;

import lombok.*;


@Data 
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@Entity(name="notice")
public class Notice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//이건 오토인크리먼트일때만 넣어주는거
	private int noticeNumber;
	
	private int userNumber;
	
	@Column(name="read", columnDefinition = "TINYINT", length=1)
	private int read;
	
	@Temporal(TemporalType.TIMESTAMP)	//이거 써야 자동으로 시간 넣어줌
	private Date date;
	
	private String title;
	
	private String content;
	
}
