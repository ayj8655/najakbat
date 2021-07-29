package com.mococo.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_number")
	private int postNumber;
	private int userNumber;
	private String userNickname;
	private int postType; // 1:자유, 2:정보, 3:질문, 4:나눔
	private String title;
	private int recommend;
	private int view;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private String content;
	private String keyword;
	private boolean isDelete; // 게시글이 지워졌는지 여부 - 지워졌어도 db에는 존재
	
	 @ManyToMany(mappedBy = "posts")
	 private List<User> users = new ArrayList<>();
}
