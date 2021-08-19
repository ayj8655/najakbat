package com.mococo.common.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.NoticeDAO;
import com.mococo.common.dao.UserDAO;
import com.mococo.common.model.Notice;
import com.mococo.common.model.User;

import io.jsonwebtoken.io.IOException;

@Service
public class NoticeService {

	@Autowired
	NoticeDAO noticeDAO;

	@Autowired
	UserDAO userDAO;

//	public List<Notice> findByUserNumber(int userNumber) {
//		List<Notice> notice = new ArrayList<>();
//		noticeDAO.findAll().forEach(e -> notice.add(e));
//		return notice;
//	}

	public List<Notice> findAllByUserNumber(int userNumber) {
		List<Notice> notice = noticeDAO.findAllByUserNumber(userNumber);

		return notice;
	}

	public Optional<Notice> findByNoticeNumber(int noticeNumber) {

		Optional<Notice> notice = noticeDAO.findByNoticeNumber(noticeNumber);

		notice.ifPresent(selectUser -> {

		});

		return notice;
	}

	public boolean readNotice(int noticeNumber) {

		Optional<Notice> updateNotice = noticeDAO.findByNoticeNumber(noticeNumber);

		System.out.println(updateNotice);

		// update할 post가 없는 경우
		if (!updateNotice.isPresent()) {
			return false;
		}

		updateNotice.get().setIsRead(1);

		System.out.println(updateNotice.get());
		noticeDAO.save(updateNotice.get());// .get() 안하면 옵셔널이라서 뭔지 모르기 때문에 해줘야함

		return true;
	}

	public void insertNotice(int userNumber, int postNumber, String title, String content) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date date = cal.getTime();
		Notice notice = new Notice(0, userNumber, postNumber, 0, date, title, content);

		noticeDAO.save(notice);
		
		Optional<User> user = userDAO.findByUserNumber(userNumber);
		
		if (user.get().getToken() !=null) {
			// 안드로이드 알림시작
			JSONObject jsonob = new JSONObject();
			JSONArray jsonarr = new JSONArray();

			JSONObject data = new JSONObject();
			jsonob.put("to", user.get().getToken());
			jsonob.put("priority", "high");
			data.put("title", title);
			data.put("message", content);
			data.put("test", "test");
			jsonarr.add(data);

			String temp = jsonarr.toString();

			temp = temp.substring(1);
			temp = temp.substring(0, temp.length() - 1);
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(temp);
			JSONObject jsonObj = (JSONObject) obj;
			jsonob.put("data", obj);

			
			try {

				String host_url = "https://fcm.googleapis.com/fcm/send";
				String AUTH_KEY_FCM = "AAAAPeLDrPw:APA91bFCQ0NVknac7MqYN0BIei1QuesK7yttX_T8pR4k5S5AKQT34OURsG7hevsQK7el-Wa58v0gFr6zj-Uqzvs43c_5QreSlkppB4h4VenM2pxpotohSyVyGSQBwqXPq3HScTPhyJqU";

				HttpURLConnection conn = null;

				URL url = new URL(host_url);
				conn = (HttpURLConnection) url.openConnection();

				conn.setRequestMethod("POST");// POST GET
				conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
				conn.setRequestProperty("Content-Type", "application/json");

				// POST방식으로 스트링을 통한 JSON 전송
				conn.setDoOutput(true);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

				System.out.println(jsonob.toString());
				bw.write(jsonob.toString());
				bw.flush();
				bw.close();

				// 서버에서 보낸 응답 데이터 수신 받기
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String returnMsg = in.readLine();
				System.out.println("응답메시지 : " + returnMsg);

				// HTTP 응답 코드 수신
				int responseCode = conn.getResponseCode();
				if (responseCode == 400) {
					System.out.println("400 : 명령을 실행 오류");
				} else if (responseCode == 500) {
					System.out.println("500 : 서버 에러.");
				} else { // 정상 . 200 응답코드 . 기타 응답코드
					System.out.println(responseCode + " : 응답코드임");
				}

			} catch (IOException ie) {
				System.out.println("IOException " + ie.getCause());
				ie.printStackTrace();
			} catch (Exception ee) {
				System.out.println("Exception " + ee.getCause());
				ee.printStackTrace();
			}

				//안드로이드 알림 끝
		}
		
		

	}

	public int deleteAllByIdIn(List<Integer> noticeno) {

		return noticeDAO.deleteAllByNoticeNumberIn(noticeno);
	}

	public void deleteAllByUserNumber(int userNumber) {

		noticeDAO.deleteAllByUserNumber(userNumber);
	}

}
