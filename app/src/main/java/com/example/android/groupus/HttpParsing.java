package com.example.android.groupus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by littlejkim on 05/04/2018.
 */

public class HttpParsing {
    String data = "";
    String result ;
    BufferedWriter bufferedWriter ;
    OutputStream outputStream ;
    BufferedReader bufferedReader ;
    StringBuilder stringBuilder = new StringBuilder();
    URL url;

    public String postRequest(HashMap<String, String> Data, String HttpUrlHolder) {

        try {
            url = new URL(HttpUrlHolder);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            outputStream = httpURLConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(FinalDataParse(Data));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                httpURLConnection.getInputStream()
                        )
                );
                data = bufferedReader.readLine();
            }
            else {
                data = "Error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public String FinalDataParse(HashMap<String,String> hashMap2) throws UnsupportedEncodingException {

        for(Map.Entry<String,String> map_entry : hashMap2.entrySet()){
            stringBuilder.append("&");
            stringBuilder.append(URLEncoder.encode(map_entry.getKey(), "UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(map_entry.getValue(), "UTF-8"));
        }
        result = stringBuilder.toString();
        return result ;
    }
}
