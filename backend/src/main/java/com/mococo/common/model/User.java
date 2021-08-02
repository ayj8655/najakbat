package com.mococo.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import lombok.*;


@Data 
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@Entity(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//이건 오토인크리먼트일때만 넣어주는거
	@Column(name = "user_number")
	private int userNumber;
	
	
	private String id;
	private String password;
	private String nickname;
	private String phone;
	@Temporal(TemporalType.TIMESTAMP)	//이거 써야 자동으로 시간 넣어줌
	private Date joinDate;
	private String address;
	private int gold;
	private boolean activated;

	// 유저와 post recommend는 N:M관계
	@ManyToMany
	@JoinTable(
			name ="post_recommend",
			joinColumns = @JoinColumn(name ="user_number"),
			inverseJoinColumns = @JoinColumn(name = "post_number")
			)
	private List<Post> posts = new ArrayList<>();
	
	// 유저와 comment recommend는 N:M관계
	@ManyToMany
	@JoinTable(
			name ="comment_recommend",
			joinColumns = @JoinColumn(name ="user_number"),
			inverseJoinColumns = @JoinColumn(name = "comment_number")
			)
	private List<Comment> comments = new ArrayList<>();
	
	
	@ManyToMany
	   @JoinTable(
	      name = "user_authority",
	      joinColumns = {@JoinColumn(name = "user_number", referencedColumnName = "user_number")},
	      inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
	   private Set<Authority> authorities;
	
	

}
