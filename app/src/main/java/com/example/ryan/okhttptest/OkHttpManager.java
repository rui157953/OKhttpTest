package com.example.ryan.okhttptest;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Ryan on 2016/7/29.
 */
public class OkHttpManager {
    private volatile static OkHttpManager ourInstance;
    private OkHttpClient okHttpClient;
    private Handler mDelivery = new Handler(Looper.getMainLooper());

    public static OkHttpManager getInstance() {
        if (ourInstance == null) {
            synchronized (OkHttpManager.class) {
                if (ourInstance == null) {
                    ourInstance = new OkHttpManager();
                }
            }
        }
        return ourInstance;
    }

    private OkHttpManager() {
        okHttpClient = new OkHttpClient();
    }

    /**
     * Get 请求
     * @param url
     * @param callbck
     */
    public void okhttpGet(String url, HttpUtils.ResultCallback callbck){
        Request request = new Request.Builder().url(url).build();
        deliveryCallback(request,callbck);
    }

    /**
     * Post请求
     * @param url
     * @param requestBody
     * @param callbck
     */
    public void okhttpPost(String url, Map<String,String> requestBody, HttpUtils.ResultCallback callbck) {
        FormBody.Builder builder = new FormBody.Builder();
        Set<String> keySet = requestBody.keySet();
        for (String key :
                keySet) {
            builder.add(key,requestBody.get(key));
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        deliveryCallback(request,callbck);
    }

    /**
     *
     * @param request
     * @param callback
     */
    private void deliveryCallback(final Request request, final HttpUtils.ResultCallback callback){
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(call,e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                final String result = response.body().string();
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(call,result);
                    }
                });
            }
        });
    }

}
