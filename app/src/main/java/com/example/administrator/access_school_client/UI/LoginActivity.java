package com.example.administrator.access_school_client.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.access_school_client.Application;
import com.example.administrator.access_school_client.MainActivity;
import com.example.administrator.access_school_client.R;
import com.google.gson.Gson;



import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

public class LoginActivity extends AppCompatActivity {
    TextView reg;
    EditText phone;
    EditText pass;
    Button login1;
    ImageButton login_back;
    private Application application;
    public SharedPreferences sp;
    public SharedPreferences.Editor editor;
    public static String ID;
    public static String isServicer;
    /*public static final String IP="http://10.151.6.156";*/
    /*public static final String IP="http://10.153.28.23";*/
    /*public static final String IP="http://192.168.43.50";*/
    public static final String IP="http://10.113.8.14";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application=(Application)getApplicationContext();//获得应用程序Application

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        init();
    }

    public class Info1{
        public int status;
        public User user;
    }
    public class User{
        public String id;
        public String username;
        public String isservicer;
    }
    private void init() {
        //2017.7.23




        reg = (TextView)findViewById(R.id.reg);
        phone = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.pass);
        login1 = (Button)findViewById(R.id.login2);
        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sp.edit();
        if(!sp.getString("phone","").equals("")){
            phone.setText(sp.getString("phone",""));
            pass.setText(sp.getString("password",""));
        }
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                FinalHttp post = new FinalHttp();
                AjaxParams params = new AjaxParams();
                params.put("phone",phone.getText().toString());
                params.put("password",pass.getText().toString());
                post.post(IP +"/express/User/login", params, new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object t) {
                        super.onSuccess(t);
                        if(phone.length()==0||pass.length()==0){
                            Toast.makeText(LoginActivity.this,"手机号或密码不能为空",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {
                                Gson gson = new Gson();
                                //JsonObject jsonobject = gson.fromJson(t.toString(),JsonObject.class);
                                Info1 info = gson.fromJson(t.toString(),Info1.class);

//                                //Log.i("jsonobject",jsonobject);
                                String s = String.valueOf(info.status);
//
//                                //startActivity(new Intent(LoginActivity.this,RegisterActivity.class));


                                    Intent intent  = new Intent(LoginActivity.this,MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("loginstate","已登录");
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                                }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                });*/
//
                /**
                 * 重写登陆
                 * @author:俞泽峰
                 */
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterTwoActivity.class));
                finish();
            }
        });

    }
}

