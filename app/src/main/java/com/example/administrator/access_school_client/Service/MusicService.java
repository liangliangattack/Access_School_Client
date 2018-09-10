package com.example.administrator.access_school_client.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class MusicService extends Service {
//    private String path = "mnt/sdcard/123.mp3";
    private MediaPlayer player;
    private String source;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        source = intent.getStringExtra("source");
        Toast.makeText(this, ""+source+"00000", Toast.LENGTH_SHORT).show();
        player = new MediaPlayer();
        try {
            Toast.makeText(this, ""+source+"1111111", Toast.LENGTH_SHORT).show();
            StringBuffer stringBuffer = new StringBuffer("http://dict.youdao.com/dictvoice?audio=");
            stringBuffer.append(source);
            player.setDataSource(stringBuffer.toString());
            //准备资源
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("服务", "准备播放音乐");
        Log.e("服务", "开始");
//        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        //当执行完了onCreate后，就会执行onBind把操作歌曲的方法返回
        source = intent.getStringExtra("source");
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //这里只执行一次，用于准备播放器

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    //该方法包含关于歌曲的操作
    public class MyBinder extends Binder {

        //判断是否处于播放状态
        public boolean isPlaying(){
            return player.isPlaying();
        }

        //播放或暂停歌曲
        public void play() {
            if (player.isPlaying()) {
                player.pause();
            } else {
                player.start();
            }
            Log.e("服务", "播放音乐");
        }

//        //返回歌曲的长度，单位为毫秒
//        public int getDuration(){
//            return player.getDuration();
//        }
//
//        //返回歌曲目前的进度，单位为毫秒
//        public int getCurrenPostion(){
//            return player.getCurrentPosition();
//        }
//
//        //设置歌曲播放的进度，单位为毫秒
//        public void seekTo(int mesc){
//            player.seekTo(mesc);
//        }
    }
}

