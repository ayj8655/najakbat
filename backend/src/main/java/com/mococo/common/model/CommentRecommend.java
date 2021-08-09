package com.mococo.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="comment_recommend")
@IdClass(CommentRecommendPK.class)
public class CommentRecommend implements Serializable{
	@Id
	@Column(name = "comment_number")
	private int commentNumber;
	
	@Id
	@Column(name = "user_number")
	private int userNumber;
}
