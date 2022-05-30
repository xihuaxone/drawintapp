package com.example.drawintapp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.drawintapp.domain.ApiResult;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HttpClient {
    private static String host = "http://192.168.1.102:8081/";
    private static String token = null;
    private static final MediaType mediaTypeDefault = MediaType.get("application/json; charset=utf-8");

    public static void refreshToken(String token) {
        HttpClient.token = token;
    }

    public static String getToken() {
        return token;
    }

    public static boolean isTokenEmpty() {
        return token == null || token.trim().equals("");
    }

    public static <T> ApiResult<T> get(String api) {
        OkHttpClient httpClient = createClient();
        Request request = new Request.Builder().url(host + api).header("Authorization", token == null ? "" : token).get().build();
        Response response;
        try {
            response = httpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException("httpclient execute get error", e);
        }

        if (response.code() != 200) {
            throw new RuntimeException(String.format("http request for api %s error", api));
        }

        String responseStr;
        try {
            responseStr = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return JSON.parseObject(responseStr, new TypeReference<ApiResult<T>>() {});
    }

    public static <T> ApiResult<T> post(String api, String data) {
        OkHttpClient httpClient = createClient();
        RequestBody requestBody = RequestBody.create(data, mediaTypeDefault);
        Request request = new Request.Builder().url(host + api).header("Authorization", token == null ? "" : token).post(requestBody).build();

        Response response;
        try {
            response = httpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException("httpclient execute post error", e);
        }
        if (response.code() != 200) {
            throw new RuntimeException(String.format("http request for api %s error", api));
        }
        String responseStr;
        try {
            responseStr = Objects.requireNonNull(response.body()).string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ApiResult<T> apiResult = JSON.parseObject(responseStr, new TypeReference<ApiResult<T>>() {});
        apiResult.setAuthorization(response.header("Authorization", null));
        return apiResult;
    }

    public static <T> ApiResult<T> delete(String api) {
        OkHttpClient httpClient = createClient();
        Request request = new Request.Builder().url(host + api).header("Authorization", token == null ? "" : token).delete().build();
        Response response;
        try {
            response = httpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException("httpclient execute delete error", e);
        }

        if (response.code() != 200) {
            throw new RuntimeException(String.format("http request for api %s error", api));
        }

        String responseStr;
        try {
            responseStr = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return JSON.parseObject(responseStr, new TypeReference<ApiResult<T>>() {});
    }

    private static OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }
}
