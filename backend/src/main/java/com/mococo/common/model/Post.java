package com.mococo.common.model;

import java.util.Date;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@Entity(name="post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postNumber;
	
	private int userNumber;
	private int postType; // 1:자유, 2:정보, 3:질문, 4:나눔
	private String title;
	private int recommend;
	private int view;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private String content;
	private String keyword;
	private int isDelete; // 게시글이 지워졌는지 여부 - 지워졌어도 db에는 존재
	
	
}
