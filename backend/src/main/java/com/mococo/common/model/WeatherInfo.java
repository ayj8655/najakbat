package com.mococo.common.model;

import java.io.Serializable;
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
@Entity(name="weather_info")
@IdClass(WeatherInfoPK.class)
public class WeatherInfo implements Serializable{

	
	//복합키 사용하려면 Serializable이거 해야됨
	
	
	@Id
	@Column(name = "date")
	private Date date;
	
	@Id
	@Column(name = "city")
	private String city;
	
	@Id
	@Column(name = "gugun")
	private String gugun;
	
	private double temperature;
	
	private double humidity;
	
	private String weather;
}
