package com.mococo.common.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name="qna")
public class Qna {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "qna_number")
	private int qnaNumber;
	private int userNumber;
	private String userNickname;
	private String qnaType;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private String question;
	private String answer;
	private boolean finish; // 질문완료
	
}
