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
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@IdClass(CropPricePK.class)
@Entity(name = "cropPrice")
public class CropPrice implements Serializable {
	@Id
	@Column(name = "date")
	private String date;

	@Id
	@Column(name = "crop_name")
	private String cropName;

	private int price;
}
