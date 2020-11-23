package com.ift604.bingo.dal.dao;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class GenericDataHandler {
    private final String serverPath = "http://192.168.0.138:3000";
    private final String basePath = "/bingo";
    protected final String apiPath = serverPath + basePath;

    public GenericDataHandler() {

    }


    protected String getResponseFromUrl(InputStream inputStream) throws IOException {
        String result = "";
        BufferedReader in = new BufferedReader(
                new InputStreamReader(inputStream));
        String readLine = in.readLine();
        while (readLine != null) {
            result += readLine;
            readLine = in.readLine();
        }
        in.close();

        return result;
    }

    protected String postDataToUrl(String urlPath, JSONObject jsonParams) throws IOException {
        URL url = new URL(apiPath + urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        //conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        DataOutputStream os = new DataOutputStream(conn.getOutputStream());

        os.writeBytes(jsonParams.toString());

        os.flush();
        os.close();

        //TODO MANAGE ERROR
        String response = getResponse(conn.getInputStream());
        conn.disconnect();
        return response;
    }

    private String getResponse(InputStream i) throws IOException {
        String res = "";
        InputStreamReader in = new InputStreamReader(i);
        BufferedReader br = new BufferedReader(in);
        String output;
        while ((output = br.readLine()) != null) {
            res += (output);
        }

        return res;
    }

    protected String getDataFromUrl(String urlPath) throws IOException {
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(false);
        conn.setDoInput(true);
        conn.setConnectTimeout(1000);

        conn.disconnect();

        return getResponseFromUrl(conn.getInputStream());
    }
}
