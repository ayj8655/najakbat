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
@IdClass(AchievementConditionPK.class)
@Entity(name="achievement_condition")
public class AchievementCondition implements Serializable {
	
	@Id
	@Column(name = "target")
	private String target;
	
	@Id
	@Column(name = "achieve_number")
	private int achieveNumber;
	
	private int max;
}
