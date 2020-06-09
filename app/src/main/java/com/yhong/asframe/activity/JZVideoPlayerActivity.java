package com.yhong.asframe.activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yhong.asframe.R;
import com.yhong.asframe.base.BaseActivity;
import com.yhong.asframe.widget.JZMediaIjk;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZUtils;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * Created by 17639 on 2020/6/9.
 */

public class JZVideoPlayerActivity extends BaseActivity implements View.OnClickListener{

    RelativeLayout play_rl;
    JzvdStd mVideoPlayer;

    Jzvd.JZAutoFullscreenListener mSensorEventListener;
    SensorManager mSensorManager;
    private JZDataSource mJzDataSource;
    private String url, image, title;

    @Override
    public void initIntent(Intent intent) {
        url = intent.getStringExtra("url");
        image = intent.getStringExtra("image");
        title = intent.getStringExtra("title");
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        setContent(R.layout.activity_jzvideo_player);
    }

    @Override
    public void initView() {
        super.initView();
        play_rl =  findViewById(R.id.play_rl);
        play_rl.setOnClickListener(this);
        mVideoPlayer = findViewById(R.id.video_player);
    }

    @Override
    public void initData() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorEventListener = new Jzvd.JZAutoFullscreenListener();

        playVideo(url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        //home back
        Jzvd.goOnPlayOnResume();
    }

    /**
     * 播放课程
     *
     * @param url
     */
    private void playVideo(String url) {
//        url = "http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4";
        mJzDataSource = new JZDataSource(url, "");
//        ijk
        mVideoPlayer.setUp(mJzDataSource, JzvdStd.SCREEN_NORMAL, JZMediaIjk.class);  //  ijkMediaPlayer
////        Mediaplayer
//        mVideoPlayer.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
//                , "", JzvdStd.SCREEN_NORMAL, JZMediaSystem.class);
////        exo
//        mVideoPlayer.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
//                , "", JzvdStd.SCREEN_NORMAL, JZMediaExo.class);  //exo

        Glide.with(this).load(image).into(mVideoPlayer.posterImageView);
        mVideoPlayer.startVideo();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener);
        JZUtils.clearSavedProgress(this, null);
        //home back
        Jzvd.goOnPlayOnPause();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Jzvd.releaseAllVideos();
    }


    @Override
    public void onClick(View v) {
        finish();


        Jzvd.releaseAllVideos();
    }
}
