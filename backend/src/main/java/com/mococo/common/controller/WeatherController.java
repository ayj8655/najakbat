package com.mococo.common.controller;

import java.util.Optional;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mococo.common.model.WeatherInfo;
import com.mococo.common.service.WeatherService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/weather")
public class WeatherController {

	private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	
	@Autowired
	WeatherService weatherService;
	
	
	//성공하면 날씨반환 실패하면 null
	//예시 - http://localhost:8080/weather/서울특별시,서초구
	@GetMapping("/{weatherarea}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@ApiOperation(value = "시와구를 , 기준으로 입력하면 날씨를 반환, 없을시 null", response = WeatherInfo.class)
	public ResponseEntity<Optional<WeatherInfo>> getWeatherArea(@PathVariable String weatherarea) throws Exception {
		
		StringTokenizer st = new StringTokenizer(weatherarea, ",");
		String city = st.nextToken();
		String gugun = st.nextToken();
		
		//weatherService.findByCityAndGugun(city, gugun);		
		
		
		return ResponseEntity.ok(weatherService.findByCityAndGugun(city, gugun));
	}
	
	
	
}
