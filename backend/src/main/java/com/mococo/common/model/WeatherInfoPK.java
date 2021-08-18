package com.mococo.common.model;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeatherInfoPK implements Serializable{

	
	//복합키 사용하려면 Serializable이거 해야됨

	
	private String city;
	
	private String gugun;
	
}
