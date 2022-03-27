package com.helper;

import java.io.*;
import java.net.*;

public class HTTPHelper {
    public static InputStream makeHttpRequest(String urlStr) {
        InputStream responseStream = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");

            responseStream = connection.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStream;
    }
}
