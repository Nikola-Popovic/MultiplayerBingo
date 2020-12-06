package com.ift604.bingo.dal.dao;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.ift604.bingo.util.ApplicationConstants;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class GenericDataHandler {
    private final String serverPath = ApplicationConstants.SERVER_PATH;
    private final String basePath = "/bingo";
    protected final String apiPath = serverPath + basePath;

    public GenericDataHandler() {

    }

    protected ANResponse postDataToUrl(String urlPath, JSONObject jsonParams) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        ANRequest request = AndroidNetworking.post(apiPath + urlPath)
                .setPriority(Priority.HIGH)
                .addJSONObjectBody(jsonParams)
                .addHeaders("Accept", "application/json")
                .addHeaders("Content-Type", "application/json")
                .build();


        ANResponse response = request.executeForString();
        return response;
    }

    protected ANResponse putDataToUrl(String urlPath, JSONObject jsonParams) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        ANRequest request = AndroidNetworking.put(apiPath + urlPath)
                .setPriority(Priority.HIGH)
                .addJSONObjectBody(jsonParams)
                .addHeaders("Accept", "application/json")
                .addHeaders("Content-Type", "application/json")
                .build();


        ANResponse response = request.executeForString();
        return response;
    }


    protected ANResponse deleteDataToUrl(String urlPath, JSONObject jsonParams) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        ANRequest request = AndroidNetworking.delete(apiPath + urlPath)
                .setPriority(Priority.HIGH)
                .addJSONObjectBody(jsonParams)
                .addHeaders("Accept", "application/json")
                .addHeaders("Content-Type", "application/json")
                .build();


        ANResponse response = request.executeForString();
        return response;
    }

    protected ANResponse getDataFromUrl(String urlPath) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        ANRequest request = AndroidNetworking.get(apiPath + urlPath)
                .setPriority(Priority.HIGH)
                .addHeaders("Accept", "application/json")
                .addHeaders("Content-Type", "application/json")
                .build();


        ANResponse response = request.executeForString();
        return response;
    }
}
