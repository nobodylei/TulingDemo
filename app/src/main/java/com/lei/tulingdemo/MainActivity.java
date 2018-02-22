package com.lei.tulingdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements HttpGetDataListener, OnClickListener{

    private HttpData httpData;
    private List<ListData> list;
    private ListView lv;
    private EditText sendText;
    private Button send_btn;
    private String content_str;
    private TextAdapter adapter;
    private String[] welcome_array;
    private double currentTime, oldTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        list = new ArrayList<ListData>();
        lv = findViewById(R.id.lv);
        sendText = findViewById(R.id.sendText);
        send_btn = findViewById(R.id.send_btn);
        send_btn.setOnClickListener(this);
        adapter = new TextAdapter(list, this);
        lv.setAdapter(adapter);
        ListData listData = new ListData(getRandomWelcomeTips(), ListData.RECIVER,getTime());
        list.add(listData);
    }

    /**
     * 欢迎语
     * @return
     */
    private String getRandomWelcomeTips() {
        String welcome = null;
        welcome_array = this.getResources().getStringArray(R.array.welcome_tips);
        int index = (int)(Math.random() * (welcome_array.length - 1));
        welcome = welcome_array[index];
        return welcome;
    }

    @Override
    public void getDataUrl(String data) {
        //Log.i("info", data);
        parseText(data);
    }

    public void parseText(String str) {
        try {
            JSONObject jb = new JSONObject(str);
            String text = jb.getString("text");
            ListData listData = new ListData(text, ListData.RECIVER, getTime());
            list.add(listData);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        getTime();
        content_str = sendText.getText() + "";
        sendText.setText("");
        String content_str2 = content_str.replace(" ","");
        content_str2 = content_str.replace("\n","");
        ListData listData = new ListData(content_str, ListData.SEND, getTime());
        list.add(listData);
        if(list.size() > 50) {
            for(int i = 0; i < 20; i++) {
                list.remove(i);
            }
        }
        adapter.notifyDataSetChanged();
        httpData = (HttpData) new HttpData("http://www.tuling123.com/openapi/api?key=bdae886baa234e17bdba421783d178de&info="
                + content_str2,
                this)
                .execute();
    }

    private String getTime() {
        currentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDate = new Date();
        String str = format.format(currentDate);
        if(currentTime - oldTime >= 5 * 60 * 1000) {
            oldTime = currentTime;
            return str;
        }

        return "";
    }
}
