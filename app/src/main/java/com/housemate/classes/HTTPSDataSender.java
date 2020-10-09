package com.housemate.classes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.concurrent.Callable;

import javax.net.ssl.HttpsURLConnection;

public class HTTPSDataSender implements Callable {
    URL url;
    String data;

    public HTTPSDataSender(URL url, String data) {
        this.url = url;
        this.data = data;
    }

    @Override
    public String[] call() throws RuntimeException {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");

            OutputStream outputStream = connection.getOutputStream();
            byte[] requestData = data.getBytes("utf-8");
            outputStream.write(requestData, 0, requestData.length);
            outputStream.close();

            if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                throw new RuntimeException();
            }

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String[] responseLines = bufferedReader.lines().toArray(String[]::new);
            inputStream.close();

            connection.disconnect();
            return responseLines;
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server, please try again");
        }
    }
}
