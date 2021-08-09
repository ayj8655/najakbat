package com.mococo.common.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@IdClass(WaterRecordPK.class)
@Entity(name="waterRecord")
public class WaterRecord {
	@Id
	@Column(name = "user_crop_number")
	private int userCropNumber;
	@Id
	@Column(name = "record_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordDate;
}
