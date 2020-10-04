package com.housemate.classes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;

import javax.net.ssl.HttpsURLConnection;

public class HTTPSDataReceiver implements Callable {
    URL url;

    public HTTPSDataReceiver(URL url) {
        this.url = url;
    }

    @Override
    public String[] call() {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");

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
            throw new RuntimeException("Error communicating with server");
        }
    }
}
