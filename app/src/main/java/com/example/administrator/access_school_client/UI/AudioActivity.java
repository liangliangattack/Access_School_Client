package com.example.administrator.access_school_client.UI;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.Service.MusicService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/9/4 14:02.
 */
public class AudioActivity extends AppCompatActivity{

    private ImageView play;
    private EditText english;
    private MyConnection conn;
    private MusicService.MyBinder musicControl;
    private Button transbtn;
    private Boolean isBind = false;
//    private SeekBar seekBar;
//    private static final int UPDATE_PROGRESS = 0;
    private Handler handler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 1:
                updatePlayImg();
                break;
            case 2:
                break;
        }
    }
};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_audio);
        play = findViewById(R.id.play);
        english = findViewById(R.id.english);
        transbtn = findViewById(R.id.trans);
        conn = new MyConnection();

        transbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在这里启动音乐播放服务
//                if (isEnglish(english.getText().toString()).equals("english")) {
                Intent intent3 = new Intent(AudioActivity.this, MusicService.class);
                    //使用混合的方法开启服务
                    intent3.putExtra("source", english.getText().toString());
                    startService(intent3);
                    bindService(intent3, conn, BIND_AUTO_CREATE);
                    isBind = true;
                    Toast.makeText(AudioActivity.this, "正在启动", Toast.LENGTH_SHORT).show();
                    play.setVisibility(View.VISIBLE);
//                }
//                else {
//                    Toast.makeText(AudioActivity.this, "请输入英文单词....", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play.setVisibility(View.GONE);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //播放
                play();
            }
        });
    }

    private String isEnglish(String text){
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(text);
        if(m.matches() ){
            Toast.makeText(AudioActivity.this,"输入的是数字", Toast.LENGTH_SHORT).show();
            return "math";
        }
        p=Pattern.compile("[a-zA-Z]");
        m=p.matcher(text);
        if(m.matches()){
            Toast.makeText(AudioActivity.this,"输入的是字母", Toast.LENGTH_SHORT).show();
            return "english";
        }
        p=Pattern.compile("[\u4e00-\u9fa5]");
        m=p.matcher(text);
        if(m.matches()){
            Toast.makeText(AudioActivity.this,"输入的是汉字", Toast.LENGTH_SHORT).show();
            return "zhong";
        }
        else {
            return "null";
        }
    }

    private class MyConnection implements ServiceConnection {

        //服务启动完成后会进入到这个方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获得service中的MyBinder
            musicControl = (MusicService.MyBinder) service;


            //更新按钮
            updatePlayImg();
            //设置进度条的最大值
//            seekBar.setMax(musicControl.getDuration());
            //设置进度条的进度
//            seekBar.setProgress(musicControl.getCurrenPostion());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //进入到界面后开始更新进度条
//        if (musicControl != null){
//            handler.sendEmptyMessage(UPDATE_PROGRESS);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出应用后与service解除绑定
        if(isBind) {
            unbindService(conn);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止更新进度条的进度
//        handler.removeCallbacksAndMessages(null);
    }

    //更新进度条
    private void updateProgress() {
//        int currenPostion = musicControl.getCurrenPostion();
//        seekBar.setProgress(currenPostion);
//        //使用Handler每500毫秒更新一次进度条
//        handler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 500);
    }


    //更新按钮的文字
    public void updatePlayImg() {
        if (musicControl.isPlaying()) {
            play.setImageResource(R.drawable.stop);
//            handler.sendEmptyMessage(UPDATE_PROGRESS);
        } else {
            play.setImageResource(R.drawable.play);
        }
        handler.sendEmptyMessageDelayed(1,500);
    }

    //调用MyBinder中的play()方法
    public void play() {
        musicControl.play();
        updatePlayImg();
    }

}
