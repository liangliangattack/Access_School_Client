package com.example.administrator.access_school_client.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.bean.HistoryEvent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/9/6 11:11.
 */
public class HistoryActivity extends AppCompatActivity implements View.OnClickListener{

    List<HistoryEvent> historyEvents = new ArrayList<HistoryEvent>();
    Button change;
    ImageView iv;
    TextView tv;
    Boolean isflag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_history);

        iv = findViewById(R.id.iv_sy);
        tv = findViewById(R.id.tv_sy);
        change = findViewById(R.id.change);
        change.setOnClickListener(this);

        if(!isflag) {
            //缓存历史的今天
            Mypost();
            isflag = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change:
                Mypost();
                break;
        }
    }

    private void Mypost() {
        FinalHttp http = new FinalHttp();
        AjaxParams params = new AjaxParams();
        params.put("dispose","detail");
        params.put("key","jiahengfei");
        Random random = new Random();
        params.put("month",String.valueOf(random.nextInt(12)));
        params.put("day",String.valueOf(random.nextInt(29)));

        //http://www.jiahengfei.cn:33550/port/history?dispose=detail&key=jiahengfei&month=4&day=1
        http.post("http://www.jiahengfei.cn:33550/port/history",params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                Log.e("FFFFFFFFFFFFFFFFFSY","onsuccsess");
                history(o);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(getBaseContext(), strMsg+"", Toast.LENGTH_SHORT).show();
                Log.e("FFFFFFFFFFFFFFFFFSY","failer"+strMsg);
            }
        });
    }

    private void history(Object o) {
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(o.toString(),JsonObject.class);
            JSONArray jsonArray = new JSONArray(jsonObject.get("data").toString());
            historyEvents.removeAll(historyEvents);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                HistoryEvent event = new HistoryEvent(jsonObject1.getString("title"),
                        jsonObject1.getString("year")+"年"+jsonObject1.getString("lunar"),
                        jsonObject1.getString("pic"));
                historyEvents.add(event);
            }
//            change1();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(historyEvents.size() > 4) {
            Random random = new Random();
            int position = random.nextInt(4);
            if (historyEvents.get(position).getPicurl() != null) {
                Glide.with(this).load(historyEvents.get(position).getPicurl()).into(iv);
            } else {
            }
            tv.setText("————" + historyEvents.get(position).getTitle() + "\n" + historyEvents.get(position).getDate());
        }
        else{
            Toast.makeText(this, "error....please try again", Toast.LENGTH_SHORT).show();
        }
    }
}
