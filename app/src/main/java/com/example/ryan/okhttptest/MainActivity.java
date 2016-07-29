package com.example.ryan.okhttptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonget = (Button) findViewById(R.id.button);
        Button buttonpost = (Button) findViewById(R.id.button2);
        textView = (TextView) findViewById(R.id.text);
        buttonget.setOnClickListener(this);
        buttonpost.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                HttpUtils.requestGet("https://www.baidu.com/", new HttpUtils.ResultCallback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        textView.setText(e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, String response) {
                        textView.setText(response);
                    }
                });

                break;
            case R.id.button2:
                Map<String, String> body = new HashMap<>();
                body.put("menu", "秘制红烧肉");
                body.put("key", "c8b0bbadfe77e6b95ff73617c5ffd8d8");
                HttpUtils.requestPost("http://apis.juhe.cn/cook/query.php", body, new HttpUtils.ResultCallback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        textView.setText(e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, String response) {
                        textView.setText(response);
                    }
                });
                break;
        }
    }


}
