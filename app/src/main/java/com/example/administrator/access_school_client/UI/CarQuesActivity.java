package com.example.administrator.access_school_client.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.*;
import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.bean.CarQues;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarQuesActivity extends AppCompatActivity {

    private ListView listView;
    private List<CarQues> Queses = new ArrayList<CarQues>();;
    private String CARQUESESURL = "http://v.juhe.cn/jztk/query";
    private Button randbtn;
    private Button listbtn;
    private int subject = 1;
    private String model = "c2";
    private Spinner subsp;
    private Spinner modsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carques);
        initView();

        randbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(CarQuesActivity.this, "dianji ", Toast.LENGTH_SHORT).show();
                requestCarQueses(true);
            }
        });
        listbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCarQueses(false);
            }
        });
        subsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString().equals("科目一")) {
                    subject = 1;
                }
                else if(adapterView.getItemAtPosition(i).toString().equals("科目四")){
                    subject = 4;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                subject = 1;
            }
        });
        modsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                model = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                model = "c2";
            }
        });
    }

    //根据是否随机来请求驾考题目数据
    private void requestCarQueses(Boolean isRand) {
        FinalHttp post = new FinalHttp();
        AjaxParams params = new AjaxParams();
        params.put("subject", String.valueOf(subject));
        params.put("model",model);
        params.put("key","db977b801b1e0b9b3260cf02aff82bbb");
        if(isRand){
            params.put("testType","rand");
        }
        else{
            params.put("testType","order");
        }
        post.post(CARQUESESURL, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                handle(o);
            }
        });

    }

    public void handle(Object o){
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(o.toString(), JsonObject.class);
            JSONArray jsonArray = new JSONArray(jsonObject.get("result").toString());
            Queses.removeAll(Queses);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject json1 = jsonArray.getJSONObject(i);
                CarQues carQues = new CarQues(
                        json1.getString("id")+".",
                        json1.getString("question"),
                        json1.getString("answer"),
                        "A  "+json1.getString("item1"),
                        "B  "+json1.getString("item2"),
                        "C  "+json1.getString("item3"),
                        "D  "+json1.getString("item4"),
                        "解析：\n"+json1.getString("explains"),
                        json1.getString("url")
                );
                switch (Integer.parseInt(carQues.getAnswer())){
                    default:
                        break;
                    case 1:
                        carQues.setAnswer("正确答案是："+"A");
                        break;
                    case 2:
                        carQues.setAnswer("正确答案是："+"B");
                        break;
                    case 3:
                        carQues.setAnswer("正确答案是："+"C");
                        break;
                    case 4:
                        carQues.setAnswer("正确答案是："+"D");
                        break;
                }
                Queses.add(carQues);
            }
            listView.setAdapter(new MyLVAdapter());
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        modsp = findViewById(R.id.model_sp);
        subsp = findViewById(R.id.subject_sp);
        randbtn = findViewById(R.id.randques_btn);
        listbtn = findViewById(R.id.listques_btn);
        listView = findViewById(R.id.carques_lv);
    }

    class MyLVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Queses.size();
        }

        @Override
        public Object getItem(int i) {
            return Queses.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(CarQuesActivity.this);
            View rootview = inflater.inflate(R.layout.simplecarquesitem , null);
            TextView tv_subjectId = (TextView) rootview.findViewById(R.id.tv_subjectId);
            TextView tv_subjectQuestion = (TextView) rootview.findViewById(R.id.tv_subjectQuestion);
            ImageView iv_subjectPic = (ImageView) rootview.findViewById(R.id.iv_subjectPic);
            TextView tv_subjectItem1 = (TextView) rootview.findViewById(R.id.tv_subjectItem1);
            TextView tv_subjectItem2 = (TextView) rootview.findViewById(R.id.tv_subjectItem2);
            TextView tv_subjectItem3 = (TextView) rootview.findViewById(R.id.tv_subjectItem3);
            TextView tv_subjectItem4 = (TextView) rootview.findViewById(R.id.tv_subjectItem4);
            TextView tv_subjectAnswer = (TextView) rootview.findViewById(R.id.tv_subjectAnswer);
            TextView tv_subjectExplains = (TextView) rootview.findViewById(R.id.tv_subjectExplains);

            tv_subjectId.setText(Queses.get(i).getId());
            tv_subjectQuestion.setText(Queses.get(i).getQuestion());
            Glide.with(CarQuesActivity.this).load(Queses.get(i).getPicUrl()).into(iv_subjectPic);
            tv_subjectItem1.setText(Queses.get(i).getItem1());
            tv_subjectItem2.setText(Queses.get(i).getItem2());
            tv_subjectItem3.setText(Queses.get(i).getItem3());
            tv_subjectItem4.setText(Queses.get(i).getItem4());
            tv_subjectAnswer.setText(Queses.get(i).getAnswer());
            tv_subjectExplains.setText(Queses.get(i).getExplains());

            return rootview;
        }
    }
}
