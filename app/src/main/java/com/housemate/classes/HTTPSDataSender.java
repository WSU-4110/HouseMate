package com.housemate.classes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.net.ssl.HttpsURLConnection;

public class HTTPSDataSender implements Callable {
    URL url;
    String data;
    private static final String WEB_ADDRESS = "https://housemateapp1.000webhostapp.com/";

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

    public static String[] initiateTransaction(String script, String data) throws ExecutionException, InterruptedException, MalformedURLException {
        String scriptAddress = WEB_ADDRESS + script;
        URL url = new URL(scriptAddress);
        HTTPSDataSender sender = new HTTPSDataSender(url, data);
        FutureTask<String[]> senderTask = new FutureTask<>(sender);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(senderTask);
        return senderTask.get();
    }

    public static String mapToJson(Object o) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String data = objectMapper.writeValueAsString(o);
        return data;
    }
}
