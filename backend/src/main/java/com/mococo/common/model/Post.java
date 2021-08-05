package com.mococo.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name="post")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class) // N:M관계에서 서로 참조할때 무한루프 안빠지게 하는 어노테이션
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
	private int commentCount;
	private int view;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private String content;
	private String keyword;
	private boolean isDelete; // 게시글이 지워졌는지 여부 - 지워졌어도 db에는 존재
	
	
	 @ManyToMany(mappedBy = "posts",fetch = FetchType.LAZY)
	 private List<User> users = new ArrayList<>();
	 
	 
	 
     @OneToMany(
     	   mappedBy = "post",
     	   cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
     	   orphanRemoval = true
     )
     private List<PostPhoto> photos = new ArrayList<>();
     
 	
}
