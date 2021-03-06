package com.adhito.inixindo_task_individual;

import android.net.Uri;
import android.util.Log;

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

public class HttpHandler {
    // Constructor 1: Send POST request
    public String sendPostRequest(String requestUrl, HashMap<String, String> postDataParams) {
        // Membuat URL
        URL url;
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Mengirim Request dari Client ke Server
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8")
            );
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();

            // Cek HTTP status code untuk memastikan request diterima oleh server
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Response dikirim dari server ke client
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );
                sb = new StringBuilder();
                String response;
                while ((response = reader.readLine()) != null) {
                    sb.append(response);
                }
            }

        } catch (Exception ex) {
            // Error Message (Jika ada)
            ex.printStackTrace();
        }
        return sb.toString();
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    // Constructor 2: Send GET request untuk GET ALL dan GET_DETAIL
    // GET ALL
    public String sendGetResponse(String responseUrl){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(responseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String response;
            while ((response = reader.readLine()) != null){
                sb.append(response + "\n");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return sb.toString();
    }

    public String sendGetResponseDate(String responseUrl, String start, String end) {
        StringBuilder sb = new StringBuilder();
        try {
            Uri.Builder builder = new Uri.Builder();
            builder.appendQueryParameter("start", start).
                    appendQueryParameter("end", end);
            URL url = new URL(responseUrl + builder);
            Log.d("url:", String.valueOf(builder));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String response;
            while ((response = reader.readLine()) != null) {
                sb.append(response + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    // GET DETAIL
    public String sendGetResponse(String responseUrl, String id){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(responseUrl + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String response;
            while ((response = reader.readLine()) != null){
                sb.append(response + "\n");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return sb.toString();
    }
}
