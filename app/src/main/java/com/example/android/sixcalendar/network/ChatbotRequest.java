package com.example.android.sixcalendar.network;

import android.content.Context;
import android.util.Log;

import com.example.android.sixcalendar.entries.ChatbotDingding;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by jackie on 2019/1/19.
 */

public class ChatbotRequest extends BaseReqeust {
    public static String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=0645433cca666cdba4e350d5806d6ad66d44a59a8408c6ecc266095d667f3e1b";

    public static void postChatbot(Context context, ChatbotDingding info, final BaseResponse response) {
        if (!isNetworkConnected(context)) return;

        String message = "";
        try {
            URL url = new URL(WEBHOOK_TOKEN);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Content-type", "application/json;charset=utf-8");
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            /*StringBuffer sb=new StringBuffer();
            sb.append("email=");
            sb.append("409947972@qq.com&");
            sb.append("password=");
            sb.append("1234&");
            sb.append("verify_code=");
            sb.append("4fJ8");
            String param=sb.toString();
            outputStream.write(param.getBytes());*/
            Gson gson = new Gson();
            String requestInfo = gson.toJson(info);
            Log.d("ChatbotRequest", "requestInfo " + requestInfo);
            outputStream.write(requestInfo.getBytes());
            outputStream.flush();
            outputStream.close();
            int responseCode = connection.getResponseCode();
            Log.d("ChatbotRequest", "responseCode " + responseCode);
            InputStream inputStream = connection.getInputStream();
            byte[] data = new byte[1024];
            StringBuffer sb1 = new StringBuffer();
            int length = 0;
            while ((length = inputStream.read(data)) != -1) {
                String s = new String(data, Charset.forName("utf-8"));
                sb1.append(s);
            }
            message = sb1.toString().trim();
            if (response != null) {
                if (responseCode == 200) {
                    response.onSuccess(message);
                } else {
                    response.onFailure(responseCode, connection.getResponseMessage());
                }
            }
            inputStream.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            if (response != null) response.onFailure(-1, e.getMessage());
        }
        Log.d("ChatbotRequest", "message = " + message);
    }
}
