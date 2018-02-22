package com.lei.tulingdemo;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by yanle on 2018/2/22.
 * 异步通讯
 */

public class HttpData extends AsyncTask<String, Void, String>{

    private HttpClient mHttpClient;
    private HttpGet mHttpGet;//请求方式
    private HttpResponse mHttpResponse;
    private HttpEntity mHttpEntity;//创建实体
    private InputStream in;
    private HttpGetDataListener listener;

    private String url;

    public HttpData(String url, HttpGetDataListener listener) {
        this.url = url;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            mHttpClient = new DefaultHttpClient();
            mHttpGet = new HttpGet(url);
            mHttpResponse = mHttpClient.execute(mHttpGet);
            mHttpEntity = mHttpResponse.getEntity();
            in = mHttpEntity.getContent();//获取内容
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {

        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        listener.getDataUrl(s);
        super.onPostExecute(s);
    }
}
