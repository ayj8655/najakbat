package com.mococo.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WaterRecordPK implements Serializable {
	private int userCropNumber;
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordDate;
}
