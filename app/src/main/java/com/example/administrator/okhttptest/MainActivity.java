package com.example.administrator.okhttptest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * okhttp 使用复习，based on http://blog.csdn.net/lmj623565791/article/details/47911083
 */
public class MainActivity extends AppCompatActivity {

    private OkHttpClient ok;
    private TextView tv;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.tv);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    public void post(final View view){
        OkHttpClientManager.postAsyn("http://guixuan.snewfly.com/post"
                , new OkHttpClientManager.Param[]{
                        new OkHttpClientManager.Param("username", "zhy"),
                        new OkHttpClientManager.Param("password", "123")
                }
                , new OkHttpClientManager.ResultCallback<String>() {

                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e("onError", e.getMessage());
                    }

                    @Override
                    public void onResponse(final String response) {
                        //ui thread
                        tv.setText(response);
                        Snackbar.make(view, "result:" + response, Snackbar.LENGTH_LONG).show();

                    }
                }
        );

/*
        POST http://guixuan.snewfly.com/post HTTP/1.1
        Content-Type: application/x-www-form-urlencoded
        Content-Length: 25
        Host: guixuan.snewfly.com
        Connection: Keep-Alive
        Accept-Encoding: gzip
        Cookie: laravel_session=eyJpdiI6InhHcEt1VUU0QUhRWVZzaFlIZ0Y4Nnc9PSIsInZhbHVlIjoiRHZIZGJlVlhpTzBMUnZkTjhFRm9vTWFObFFkMlplcjNBOVpPZEZwcHVEVHZVdGFDWFNLUnJVakgzVjdzNjdkb2hVMU5WVUxPRHVEWlRlTzFjQ0FycEE9PSIsIm1hYyI6ImYwMTk1MWY2MTNlNjkyNDhmYWE2NDNlOTYyNjBiN2M5YzlkYWNjZmQwZTFiNDExOWY2NTdmYjhmNDJkMmVlYzgifQ%3D%3D
        User-Agent: okhttp/2.5.0

        username=zhy&password=123*/
    }

    public void get(View view){
        ok=new OkHttpClient();
        final Request request=new Request.Builder().url("https://github.com/bajian").build();
        //new call
        Call call = ok.newCall(request);

//        onResponse回调的参数是response，一般情况下，比如我们希望获得返回的字符串，可以通过response.body().string()获取；如果希望获得返回的二进制字节数组，则调用response.body().bytes()；如果你想拿到返回的inputStream，则调用response.body().byteStream()
//        看到这，你可能会奇怪，竟然还能拿到返回的inputStream，看到这个最起码能意识到一点，这里支持大文件下载，有inputStream我们就可以通过IO的方式写文件。不过也说明一个问题，这个onResponse执行的线程并不是UI线程。的确是的，如果你希望操作控件，还是需要使用handler等
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String res = response.body().string();
//                onResponse  is not in mainThread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(res);
                    }
                });

            }
        });
    }

    /**
     * json返回gson解析类
     * @param view
     */
    public void object(View view){
        OkHttpClientManager.getAsyn("http://guixuan.snewfly.com/json", new OkHttpClientManager.ResultCallback<VersionBean>() {

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(VersionBean response) {
                tv.setText(response.getData().getUrl());
            }
        });
    }

    /**
     * json返回gson解析类
     * @param view
     */
    public void bili(View view){
        OkHttpClientManager.getAsyn("http://api.bilibili.cn/list?pagesize=50&tid=29&ver=2", new OkHttpClientManager.ResultCallback<String>() {

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                tv.setText(response);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
