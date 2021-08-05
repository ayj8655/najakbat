package com.mococo.common.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.WeatherInfo;
import com.mococo.common.model.WeatherInfoPK;


@Repository
public interface WeatherInfoDAO extends JpaRepository<WeatherInfo, WeatherInfoPK>{

	Optional<WeatherInfo> findByCityAndGugun(String city, String gugun);

}
