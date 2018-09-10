package com.example.administrator.access_school_client.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.access_school_client.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mob.MobSDK;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterTwoActivity extends AppCompatActivity implements View.OnClickListener{

    //smssdk:
    private static final String TAG = "SmsYanzheng";
    EditText mEditTextPhoneNumber;
    EditText mEditTextCode;
    Button mButtonGetCode;
    Button mButtonLogin;

    EventHandler eventHandler;
    String strPhoneNumber;

    TextView login;
    //EditText phone;
    EditText gender;
    EditText user;
    EditText pass,pass2;
    //Button reg;
    private boolean isvalidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_two);
        init();

        MobSDK.init(this,"2666e52ea8643","b49d67902e4910ebdef9336e9d86b35b");

        eventHandler = new EventHandler() {

            /**
             * 在操作之后被触发
             *
             * @param event  参数1
             * @param result 参数2 SMSSDK.RESULT_COMPLETE表示操作成功，为SMSSDK.RESULT_ERROR表示操作失败
             * @param data   事件操作的结果
             */

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message message = myHandler.obtainMessage(0x00);
                message.arg1 = event;
                message.arg2 = result;
                message.obj = data;
                myHandler.sendMessage(message);
            }
        };

        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.reg1) {
            String strCode = mEditTextCode.getText().toString();
            if (null != strCode && strCode.length() == 4) {
                Log.d(TAG, mEditTextCode.getText().toString());
                SMSSDK.submitVerificationCode("86", strPhoneNumber, mEditTextCode.getText().toString());
            } else {
                Toast.makeText(this, "密码长度不正确", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.button_send_verification_code) {
            strPhoneNumber = mEditTextPhoneNumber.getText().toString();
            if (null == strPhoneNumber || "".equals(strPhoneNumber) || strPhoneNumber.length() != 11) {
                Toast.makeText(this, "电话号码输入有误", Toast.LENGTH_SHORT).show();
                return;
            }
            SMSSDK.getVerificationCode("86", strPhoneNumber);
            mButtonGetCode.setClickable(false);
            //开启线程去更新button的text
            new Thread() {
                @Override
                public void run() {
                    int totalTime = 60;
                    for (int i = 0; i < totalTime; i++) {
                        Message message = myHandler.obtainMessage(0x01);
                        message.arg1 = totalTime - i;
                        myHandler.sendMessage(message);
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    myHandler.sendEmptyMessage(0x02);
                }
            }.start();
        }
    }

    private void init() {
        mEditTextPhoneNumber = (EditText) findViewById(R.id.phone_number);
        mEditTextCode = (EditText) findViewById(R.id.verification_code);
        mButtonGetCode = (Button) findViewById(R.id.button_send_verification_code);
        mButtonLogin = (Button) findViewById(R.id.reg1);

        mButtonGetCode.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);

        login = (TextView)findViewById(R.id.login1);
        //phone = (EditText)findViewById(R.id.phone_number);
        gender = (EditText)findViewById(R.id.gender);
        user =  (EditText)findViewById(R.id.username1);
        pass = (EditText)findViewById(R.id.pass1);
        pass2 = (EditText)findViewById(R.id.pass2);
        //reg = (Button)findViewById(R.id.reg1);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterTwoActivity.this,LoginActivity.class));
                finish();
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditTextPhoneNumber.getText().length()!=11){
                    Toast.makeText(RegisterTwoActivity.this,"手机号码长度不合法",Toast.LENGTH_SHORT).show();
                }else{
                    if(user.getText()==null) {
                        Toast.makeText(RegisterTwoActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.i("pass1",pass.getText().toString());
                        Log.i("pass2",pass2.getText().toString());
                        if(!pass.getText().toString().equals(pass2.getText().toString())){
                            Toast.makeText(RegisterTwoActivity.this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                        }
                        else if(isvalidate){
                            FinalHttp post = new FinalHttp();
                            AjaxParams params = new AjaxParams();
                            params.put("phone", mEditTextPhoneNumber.getText().toString());
                            params.put("password", pass.getText().toString());
                            params.put("username", user.getText().toString());
                            params.put("gender", gender.getText().toString());
                            //params.put("entityName",entity.getText().toString());

                            post.post(LoginActivity.IP + "/express/User/register", params, new AjaxCallBack<Object>() {
                                @Override
                                public void onSuccess(Object o) {
                                    super.onSuccess(o);
                                    try {
                                        Gson gson = new Gson();
                                        JsonObject jsonobject = gson.fromJson(o.toString(), JsonObject.class);
                                        if (jsonobject.get("status").toString().equals("1")) {
                                            Toast.makeText(RegisterTwoActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterTwoActivity.this, LoginActivity.class));
                                        } else {
                                            Toast.makeText(RegisterTwoActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            });
                        }
                    }

                }
            }
        });

    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x00:
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    Log.e(TAG, "result : " + result + ", event: " + event + ", data : " + data);
                    if (result == SMSSDK.RESULT_COMPLETE) { //回调  当返回的结果是complete
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) { //获取验证码
                            Toast.makeText(RegisterTwoActivity.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "get verification code successful.");
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { //提交验证码
                            Log.d(TAG, "submit code successful");
                            Toast.makeText(RegisterTwoActivity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(RegisterTwoActivity.this, SecondActivity.class);
                            //startActivity(intent);
                            isvalidate = true;
                        } else {
                            Log.d(TAG, data.toString());
                        }
                    } else { //进行操作出错，通过下面的信息区分析错误原因
                        try {
                            Throwable throwable = (Throwable) data;
                            throwable.printStackTrace();
                            JSONObject object = new JSONObject(throwable.getMessage());
                            String des = object.optString("detail");//错误描述
                            int status = object.optInt("status");//错误代码
                            //错误代码：  http://wiki.mob.com/android-api-%E9%94%99%E8%AF%AF%E7%A0%81%E5%8F%82%E8%80%83/
                            Log.e(TAG, "status: " + status + ", detail: " + des);
                            if (status > 0 && !TextUtils.isEmpty(des)) {
                                Toast.makeText(RegisterTwoActivity.this, des, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 0x01:
                    mButtonGetCode.setText("重新发送(" + msg.arg1 + ")");
                    break;
                case 0x02:
                    mButtonGetCode.setText("获取验证码");
                    mButtonGetCode.setClickable(true);
                    break;
            }
        }
    };
}
