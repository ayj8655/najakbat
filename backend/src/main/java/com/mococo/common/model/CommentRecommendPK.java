package com.mococo.common.model;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//복합키를 설정해서 생긴 테이블을 사용할 때 필요한 것
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRecommendPK implements Serializable{
	private int commentNumber;
	private int userNumber;
	
}
