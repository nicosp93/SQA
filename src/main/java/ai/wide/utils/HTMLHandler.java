package ai.wide.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTMLHandler {

    public static String restCall(String verb, String serviceUrl, 
            String input, String contentType, String accessToken, String apiKey) {

        try {
            int maxIter = 10;
            int iter = 0;
            String jsonMessage = "";
            
            boolean isOk = false;
            while (iter < maxIter) {
                iter++;
                URL url = new URL(serviceUrl);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod(verb);
                conn.setRequestProperty("Accept-Charset", "UTF-8");
                conn.setRequestProperty("content-type", contentType + "; charset=utf-8");
                if (accessToken != null) {
                    conn.setRequestProperty("Authorization", 
                            "Bearer " + accessToken);
                }
                
                if (apiKey != null) {
                    conn.setRequestProperty("apikey", 
                            apiKey);
                }
                
                OutputStream os = conn.getOutputStream();
                os.write(input.getBytes("UTF8"));
                os.flush();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));

                    String output;
                    while ((output = br.readLine()) != null) {
                        jsonMessage = jsonMessage + output;
                    }
                    conn.disconnect();

                    System.out.println(jsonMessage);
                    isOk = true;
                    break;
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));
                    String output;
                    while ((output = br.readLine()) != null) {
                        jsonMessage = jsonMessage + output;
                    }
                    conn.disconnect();

                    System.out.println(jsonMessage);
                }
            }//while (iter < maxIter){

            if (!isOk) {
                return null;
            } else {
                return jsonMessage;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
