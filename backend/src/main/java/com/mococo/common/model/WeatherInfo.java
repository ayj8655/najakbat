package com.mococo.common.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="weather_info")
@IdClass(WeatherInfoPK.class)
public class WeatherInfo implements Serializable{

	
	//복합키 사용하려면 Serializable이거 해야됨
	
	
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
