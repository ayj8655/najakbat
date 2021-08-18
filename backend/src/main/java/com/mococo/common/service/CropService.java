package com.mococo.common.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.CropDAO;
import com.mococo.common.dao.CropPriceDAO;
import com.mococo.common.dao.MaterialDAO;
import com.mococo.common.model.Crop;
import com.mococo.common.model.CropPrice;

@Service
public class CropService {

	private static final String API_KEY = "8b73a78c-171f-487e-a211-556c771aab93";
	private static final String ID = "yj_7679";
	private static final String[] ITEM_CATEGORY_CODE = { "100", "100", "100", "100", "100", "100", "100", "100", "200",
			"200", "200", "200", "200", "200", "200", "200", "200", "200", "200", "200", "200", "200", "200", "200",
			"200", "200", "200", "200", "200", "200", "200", "200", "200", "200", "200", "200", "200", "200", "200",
			"200", "300", "300", "300", "300", "300", "300", "300", "300", "400", "400", "400", "400", "400", "400",
			"400", "400", "400", "400", "400", "400", "400", "400", "400", "400", "400" };
	private static final String[] ITEM_CODE = { "111", "112", "141", "142", "143", "144", "151", "152", "211", "212",
			"213", "214", "215", "216", "221", "222", "223", "224", "225", "226", "231", "232", "233", "241", "242",
			"243", "244", "245", "246", "247", "248", "251", "252", "253", "254", "255", "256", "257", "258", "259",
			"312", "313", "314", "315", "316", "317", "318", "319", "411", "412", "413", "414", "415", "416", "418",
			"419", "420", "421", "422", "423", "424", "425", "426", "427", "428" };

	private static final String[] ITEM_NAME = { "쌀", "찹쌀", "콩", "팥", "녹두", "메밀", "고구마", "감자", "배추", "양배추", "시금치", "상추(모종)",
			"얼갈이배추", "갓", "수박", "참외", "오이", "호박", "토마토", "딸기", "무", "당근", "열무", "건고추", "고추(모종)", "붉은고추", "피마늘", "양파", "파",
			"생강", "고춧가루", "가지", "미나리", "깻잎", "부추", "피망", "파프리카", "멜론", "깐마늘(국산)", "깐마늘(수입)", "참깨", "들깨", "땅콩", "느타리버섯",
			"팽이버섯", "새송이버섯", "호두", "아몬드", "사과", "배", "복숭아", "포도", "감귤", "단감", "바나나", "참다래", "파인애플", "오렌지", "방울토마토",
			"자몽", "레몬", "체리", "건포도", "건블루베리", "망고" };

	@Autowired
	CropDAO cropDAO;

	@Autowired
	MaterialDAO materialDAO;

	@Autowired
	CropPriceDAO cropPriceDAO;

	public List<Crop> findAll() {
		List<Crop> cropList = cropDAO.findAll();
		cropList.sort((o1, o2) -> { return o1.getName().compareTo(o2.getName()); });
		return cropList;
	}

	public List<Object> findAllSummary() {
		return cropDAO.findAllSummary();
	}

	public Optional<Crop> findByCropNumber(int cropNumber) {
		return cropDAO.findByCropNumber(cropNumber);
	}

	public List<Object> findAllRecipeByCropNumber(int cropNumber) {
		return materialDAO.findAllRecipeByCropNumber(cropNumber);
	}

	public boolean updateCropGuide(Crop crop) {
		Optional<Crop> cropOpt = cropDAO.findByCropNumber(crop.getCropNumber());
		
		if(cropOpt.isPresent()) {
			cropDAO.save(crop);
			return true;
		} else {
			return false;
		}
	}
	
	public List<Object> findCropPriceByCropNumber(int cropNumber) {
		return cropPriceDAO.findByCropNumber(PageRequest.of(0, 1), cropNumber);
	}

	public List<Object> findThirtyCropPriceByCropNumber(int cropNumber) {
		return cropPriceDAO.findByCropNumber(PageRequest.of(0, 30), cropNumber);
	}
	

	public void insertCropPrice(String startDay, String endDay) throws IOException, ParseException {
		// http://www.kamis.or.kr/service/price/xml.do?action=periodProductList&p_productclscode=02&p_startday=2015-10-01&p_endday=2015-12-01&p_itemcategorycode=200&p_itemcode=212&p_kindcode=00&p_productrankcode=04&p_countrycode=1101&p_convert_kg_yn=Y&p_cert_key=111&p_cert_id=222&p_returntype=xml
		String apiUrl = "https://www.kamis.or.kr/service/price/xml.do?action=periodProductList";
		String returnType = "json";
		String productClassCode = "02";
		String kindCode = "";
		String productRankCode = "";
		String countryCode = "";
		String convertKg = "Y";

		// 데이터를 저장할 리스트 생성
		List<CropPrice> cropPriceList = new ArrayList<CropPrice>();

//		for(int i = 0; i < 1; i++) {
		for (int i = 0; i < ITEM_CATEGORY_CODE.length; i++) {
			StringBuilder urlBuilder = new StringBuilder(apiUrl);
			urlBuilder.append("&" + URLEncoder.encode("p_productclscode", "UTF-8") + "=" + productClassCode);
			urlBuilder.append("&" + URLEncoder.encode("p_startday", "UTF-8") + "=" + startDay);
			urlBuilder.append("&" + URLEncoder.encode("p_endday", "UTF-8") + "=" + endDay);
			urlBuilder.append("&" + URLEncoder.encode("p_itemcategorycode", "UTF-8") + "=" + ITEM_CATEGORY_CODE[i]);
			urlBuilder.append("&" + URLEncoder.encode("p_itemcode", "UTF-8") + "=" + ITEM_CODE[i]);
			urlBuilder.append("&" + URLEncoder.encode("p_kindcode", "UTF-8") + "=" + kindCode);
			urlBuilder.append("&" + URLEncoder.encode("p_productrankcode", "UTF-8") + "=" + productRankCode);
			urlBuilder.append("&" + URLEncoder.encode("p_countrycode", "UTF-8") + "=" + countryCode);
			urlBuilder.append("&" + URLEncoder.encode("p_convert_kg_yn", "UTF-8") + "=" + convertKg);
			urlBuilder.append("&" + URLEncoder.encode("p_cert_key", "UTF-8") + "=" + API_KEY);
			urlBuilder.append("&" + URLEncoder.encode("p_cert_id", "UTF-8") + "=" + ID);
			urlBuilder.append("&" + URLEncoder.encode("p_returntype", "UTF-8") + "=" + returnType);

//			System.out.println(urlBuilder.toString());

			// GET으로 요청
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");

			BufferedReader br;
//			System.out.println(conn.getResponseCode());
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else if (conn.getResponseCode() == 500) {
				continue;
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			conn.disconnect();
			String data = sb.toString();

			// 데이터 가공
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(data);
			
			if (!(obj.get("data") instanceof JSONObject)) continue;
			JSONObject responseData = (JSONObject) obj.get("data");
			JSONArray items = (JSONArray) responseData.get("item");

			// 조건에 맞는 아이템 추가
			for (int j = 0; j < items.size(); j++) {
//				System.out.println(items.get(j).toString());
				JSONObject item = (JSONObject) items.get(j);
				String county = (String) item.get("countyname");

				// 평균 데이터만 사용
				if (county.equals("평균")) {
//					System.out.println(item.toString());
					CropPrice cropPrice = new CropPrice();
					cropPrice.setCropName(ITEM_NAME[i]);
					cropPrice.setDate((String)item.get("yyyy") + "/" + (String)item.get("regday"));
					cropPrice.setPrice(Integer.parseInt(((String) item.get("price")).replaceAll(",", "")));
					cropPriceList.add(cropPrice);
				}
			}
		}
		cropPriceDAO.saveAll(cropPriceList);
	}
}
