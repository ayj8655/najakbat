package com.mococo.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="comment")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_number")
	private int commentNumber;
	private int postNumber;
	private int userNumber;
	private String userNickname;
	private int parent;
	private int commentCount;
	private String content;
	private int recommend;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private boolean isDelete;
	
	@ManyToMany(mappedBy = "comments")
	private List<User> users = new ArrayList<>();
}
