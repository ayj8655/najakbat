package com.mococo.webview;

import android.content.ContentValues;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

//import static com.mococo.webview.LoginActivity.email;
//import static com.mococo.webview.LoginActivity.password;


public class RequestHttpURLConnection {
    public String request(String _url, ContentValues _params) {
        HttpURLConnection urlConn = null;
        StringBuffer sbParams = new StringBuffer();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("token", token);
        if (_params == null) {
            //sbParams.append("id=" + email + "&password=" + password + "&token=" + token);
        }

        else {
            // 파라미터가 2개 이상이면 파라미터 연결에 &가 필요하므로 스위칭할 변수 생성.

            boolean isAnd = false;
            String key;
            String value;
            for (Map.Entry<String, Object> parameter : _params.valueSet()) {
                key = parameter.getKey();
                value = parameter.getValue().toString();
                if (isAnd) sbParams.append("&");
                sbParams.append(key).append("=").append(value);
                if (true) if (_params.size() >= 2) isAnd = true;
            }
        } /** * 2. HttpURLConnection을 통해 web의 데이터를 가져온다. * */
        try {
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(15000);
            urlConn.setReadTimeout(5000);
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestMethod("POST");
            urlConn.setRequestProperty("Accept-Charset", "UTF-8");
            urlConn.setRequestProperty("Context_Type", "application/x-www-form-urlencode");
            urlConn.setRequestProperty("apikey", "abc");
            String strParams = sbParams.toString();
            Log.d("전달하는값", strParams);
            OutputStream os = urlConn.getOutputStream();
            os.write(strParams.getBytes("UTF-8"));
            os.flush();
            os.close();


            Log.d("과연성공인가", String.valueOf(urlConn.getResponseCode()));
            Log.d("과연성공인가", String.valueOf(urlConn));
            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d("과연성공인가", "실패였다 야발");
                return null;
            }


            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
            Log.d("읽어온값", String.valueOf(reader));
            String line;
            String page = "";
            while ((line = reader.readLine()) != null) {
                page += line;
            }
            return page;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null) urlConn.disconnect();
        }
        return null;
    }
}

