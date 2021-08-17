package com.mococo.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name="achievement")
public class Achievement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "achieve_number")
	private int achieveNumber;
	private String achieveName;
	private String achieveDec;
}
