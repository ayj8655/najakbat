package com.mococo.common.model;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@Entity(name="crop")
public class Crop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cropNumber;
	private String name;
	private String description;
	private String cultivation;
	private int sun;
	private int water;
	private double temperature;
	private String image;
}
