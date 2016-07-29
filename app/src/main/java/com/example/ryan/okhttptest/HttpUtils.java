package com.example.ryan.okhttptest;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Ryan on 2016/7/28.
 */
public class HttpUtils {

    public static void requestGet(String url, ResultCallback callbck){
        OkHttpManager.getInstance().okhttpGet(url,callbck);

    }

    public static void requestPost(String url, Map<String,String> requestBody,ResultCallback callbck) {
        OkHttpManager.getInstance().okhttpPost(url,requestBody,callbck);
    }

    interface ResultCallback{
        void onFailure(Call call, IOException e);
        void onResponse(Call call, String response);
    }
}
