package com.mococo.common.model;

import java.io.Serializable;
import java.util.Date;

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
@Entity(name="post_recommend")
@IdClass(PostRecommendPK.class)
public class PostRecommend implements Serializable{
	@Id
	@Column(name = "post_number")
	private int postNumber;
	
	@Id
	@Column(name = "user_number")
	private int userNumber;
	
}
