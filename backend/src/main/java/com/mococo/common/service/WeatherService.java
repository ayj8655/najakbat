package com.mococo.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mococo.common.dao.WeatherInfoDAO;
import com.mococo.common.model.WeatherInfo;

@Service
public class WeatherService {

	@Autowired
	WeatherInfoDAO weatherInfoDAO;

	public void findXY(String baseTime) throws Exception {

		String result;
		String areaTop = "서울특별시"; // 지역
		String areaMdl = "강북구";
		String areaLeaf = "종로1가동";
		String code = ""; // 지역 코드
		String x = "";
		String y = "";
		

		
		String city = "";
		String gugun = "";
		
		URL url;
		BufferedReader br;
		URLConnection conn;

		JSONParser parser;
		JSONArray jArr;
		JSONObject jobj;

		// 시 검색
		url = new URL("http://www.kma.go.kr/DFSROOT/POINT/DATA/top.json.txt");
		conn = url.openConnection();
		br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		result = br.readLine().toString();
		br.close();
		// System.out.println(result);

		parser = new JSONParser();
		jArr = (JSONArray) parser.parse(result);
		
		for (int i = 0; i < jArr.size(); i++) {
			
			jobj = (JSONObject) jArr.get(i);
			code = (String) jobj.get("code");
			city = (String) jobj.get("value");
//			System.out.println("시 이름 : " + test + ", 코드 : " + code);

			// 구 검색
			url = new URL("http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl." + code + ".json.txt");
			conn = url.openConnection();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			result = br.readLine().toString();
			br.close();
			// System.out.println(result);

//			parser = new JSONParser();
			JSONArray jArr2 = (JSONArray) parser.parse(result);
			JSONObject jobj2;
			for (int j = 0; j < jArr2.size(); j++) {
				jobj2 = (JSONObject) jArr2.get(j);
				code = (String) jobj2.get("code");
				gugun = (String) jobj2.get("value");
//				System.out.println("구 이름 : " + test2 + "코드 : " + code);

				// 동 검색
				url = new URL("http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf." + code + ".json.txt");
				conn = url.openConnection();
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				result = br.readLine().toString();
				br.close();
				// System.out.println(result);

//				parser = new JSONParser();
				JSONArray jArr3 = (JSONArray) parser.parse(result);
				JSONObject jobj3;
				if (areaMdl.equals("종로구")) {
					for (int k = 0; k < jArr3.size(); k++) {
						jobj3 = (JSONObject) jArr3.get(k);

						String leaf1 = areaLeaf.substring(0, areaLeaf.length() - 3);
						String leaf2 = areaLeaf.substring(areaLeaf.length() - 3, areaLeaf.length() - 2);
						String leaf3 = areaLeaf.substring(areaLeaf.length() - 2, areaLeaf.length());

						Pattern pattern = Pattern.compile(leaf1 + "[1-9.]{0,8}" + leaf2 + "[1-9.]{0,8}" + leaf3);
						Matcher matcher = pattern.matcher((String) jobj.get("value"));
						if (matcher.find()) {
							x = (String) jobj3.get("x");
							y = (String) jobj3.get("y");
//							System.out.println(areaLeaf + "의 x값 : " + x + ", y값 :" + y);
							break;
						}
					}
				} else {

					jobj3 = (JSONObject) jArr3.get(0);
					x = (String) jobj3.get("x");
					y = (String) jobj3.get("y");
					String test3 = (String) jobj3.get("value");
//					System.out.println(test3 + "의 x값 : " + x + ", y값 :" + y);
				}

				// 각각의 값들 다 받았음 이제 요청하고저장하면됨
				insertWeather(x, y, city, gugun, baseTime);
			}

		}

	}


	public void insertWeather(String nx, String ny, String city, String gugun,String baseTime) throws Exception {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		// String apiUrl =
		// "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst";
		// //초단기실황조회
		// String apiUrl =
		// "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtFcst";
		// //초단기예보조회
		String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"; // 동네예보조회

		String serviceKey = "JO3E4gAgxqUOYOhg27Kp8g3ppUEZh9cMc8P7DEXwLzopTW2ffCn6LFsaNxDCGztD20Jp77KkA3SNDnDhtcRY6w%3D%3D";
//		String nx = "61"; // 위도
//		String ny = "128"; // 경도
		String baseDate = sdf.format(date); // 조회하고싶은 날짜
//		String baseTime = "1400"; // API 제공 시간
		
		String dataType = "json"; // 타입 xml, json
		String numOfRows = "11"; // 한 페이지 결과 수

		//11개 하면 9시 기준 정보만 싹 가져옴
		// 동네예보 -- 전날 05시 부터 225개의 데이터를 조회하면 모레까지의 날씨를 알 수 있음

		StringBuilder urlBuilder = new StringBuilder(apiUrl);
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
		urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); // 경도
		urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); // 위도
		urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "="
				+ URLEncoder.encode(baseDate, "UTF-8")); /* 조회하고싶은 날짜 */
		urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "="
				+ URLEncoder.encode(baseTime, "UTF-8")); /* 조회하고싶은 시간 AM 02시부터 3시간 단위 */
		urlBuilder.append(
				"&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); /* 타입 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode(numOfRows, "UTF-8")); /* 한 페이지 결과 수 */

		// GET방식으로 전송해서 파라미터 받아오기
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");

		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		String data = sb.toString();

		// Json parser를 만들어 만들어진 문자열 데이터를 객체화
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);
		// response 키를 가지고 데이터를 파싱
		JSONObject parse_response = (JSONObject) obj.get("response");
		// response 로 부터 body 찾기
		JSONObject parse_body = (JSONObject) parse_response.get("body");
		// body 로 부터 items 찾기
		JSONObject parse_items = (JSONObject) parse_body.get("items");
		JSONArray parse_item = (JSONArray) parse_items.get("item");
		// JSONObject item = (JSONObject) parse_item.get("item");

		String category;
		JSONObject weather; // parse_item은 배열형태이기 때문에 하나씩 데이터를 하나씩 가져올때 사용

		// 카테고리와 값만 받아오기
		String day = "";
		String time = "";

		// TMP -> 1시간 기온 -> ℃
		// REH -> 습도 -> %
		// - 하늘상태(SKY) 코드 : 맑음(1), 구름많음(3), 흐림(4)
		// - 강수형태(PTY) 코드 : 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)

		String temperaturue = "";
		String humidity = "";
		String rearWeather = "";

		for (int i = 0; i < parse_item.size(); i++) {
			weather = (JSONObject) parse_item.get(i);
			Object fcstValue = weather.get("fcstValue");
			Object fcstDate = weather.get("fcstDate");
			Object fcstTime = weather.get("fcstTime");

//			double fcstValue = Double.parseDouble(weather.get("fcstValue").toString());
			category = (String) weather.get("category");
			// 출력
			if (!day.equals(fcstDate.toString())) {
				day = fcstDate.toString();
			}
			if (!time.equals(fcstTime.toString())) {
				time = fcstTime.toString();
				System.out.println(day + "  " + time);
			}

			if (category.equals("TMP"))
				temperaturue = (String) fcstValue;
			else if (category.equals("REH"))
				humidity = (String) fcstValue;
			else if (category.equals("PTY")) {
				if (fcstValue.equals("1")) {
					rearWeather = "비";
					continue;
				} else if (fcstValue.equals("2")) {
					rearWeather = "비/눈";
					continue;
				}else if(fcstValue.equals("3")) {
					rearWeather = "눈";
					continue;
				}else if(fcstValue.equals("4")) {
					rearWeather = "소나기";
					continue;
				}
			}else if (category.equals("SKY"))
				if (fcstValue.equals("1")) {
					rearWeather = "맑음";
				} else if (fcstValue.equals("3")) {
					rearWeather = "구름많음";
				}else if(fcstValue.equals("4")) {
					rearWeather = "흐림";
				}

//			System.out.print("\tcategory : " + category);
//			System.out.print(", fcst_Value : "+ fcstValue);
//			System.out.print(", fcstDate : "+ fcstDate);
//			System.out.println(", fcstTime : "+ fcstTime);
		}

		
		System.out.println("시 : " + city + " 구 : "+ gugun + " 온도 : " +temperaturue + " 습도 : "+ humidity + " 날씨 : "+rearWeather);
		
		
		WeatherInfo weatherInfo = WeatherInfo.builder().city(city).gugun(gugun)
				.temperature(Double.parseDouble(temperaturue)).humidity(Double.parseDouble(humidity))
				.weather(rearWeather).build();
		weatherInfoDAO.save(weatherInfo);

		
		
//		System.out.println(data);
//		for(int i=0;i<parse_item.size();i++) {
//			System.out.println(parse_item.get(i));
//		}

	}

//        else {
//        	for(int i = 0 ; i < jArr.size(); i++) {
//            	jobj = (JSONObject) jArr.get(i);
//            	if(jobj.get("value").equals(areaLeaf)) {
//            		x=(String)jobj.get("x");
//            		y=(String)jobj.get("y");
//            		System.out.println(areaLeaf+"의 x값 : "+x+", y값 :"+y);
//            		break;
//            	}
//            }
//        }

}
