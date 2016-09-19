package com.example.administrator.my_shoujiyingyin.activity;/*
 * @创建者   2016/8/24.
 * @创建时间
 * @描述   2016/8/24.
 *
 * @更新者   2016/8/24.
 * @更新时间   2016/8/24.
 * @更新描述   2016/8/24.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.bean.VideoItem;
import com.example.administrator.my_shoujiyingyin.interfaces.Keys;
import com.example.administrator.my_shoujiyingyin.manager.SystemApp;
import com.example.administrator.my_shoujiyingyin.utils.Utils;
import com.example.administrator.my_shoujiyingyin.utils.VideoView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;



public class VideoPlayerActivityOnLine extends AppCompatActivity {
    private ArrayList<VideoItem> videoItems;
    private int                  currentPosition;
    private VideoItem            currentVideoItem;
    private LinearLayout         ll_top_ctrl;
    private LinearLayout         ll_bottom_ctrl;
    private TextView             tv_title;
    private TextView             tv_system_time;
    private TextView             tv_current_position;
    private TextView             tv_duration;
    private ImageView            iv_battery;
    private SeekBar              sb_voice;
    private SeekBar              sb_video;
    private Button               btn_pre;
    private Button               btn_play;
    private Button               btn_voice;
    private Button               btn_next;
    private GestureDetector      gestureDetector;
    private static final int UPDATE_CURRENT_POSITION       = 59;
    private static final int HIDE_CTRL_LAYOUT              = 60;
    private Button       mBtn_exit;
    private int          maxVolume;
    private AudioManager mAudioManger;
    private int          mCurrentVolume;
    private boolean      mIsPlaying;
    private SharedPreferences        mSettinsSP =null;//用SharedPreferences来存储数据
    private SharedPreferences.Editor mSettinsEd =null;//使用SharedPreferences.Editor来存储数据
    private String       mTitle;
    private int          mMcurrentPosition;
    private LinearLayout mLl_loading;
    private int          mCurrentPosition;
    private VideoView    mVideoView;
    private boolean isPlaying ;
    private MediaPlayer mMediaPlayer;
    private int focusChange;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_video_player);
        SystemApp systemApp=(SystemApp) getApplication();//Application对象 应用是单例的
        systemApp.addStack(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        initView();
        initData();
        initLIstener();
        registerBatterChangedReceiver();
        initCtrlLayout();
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mVideoView.start();
        mCurrentPosition=sp.getInt(Keys.POSITION_VIDEO_CURRENT_POSITION,mVideoView.getCurrentPosition());
        tv_system_time.setText(DateFormat.format("kk:mm:ss", System.currentTimeMillis()));
        tv_current_position.setText((Utils.formatMillis(mCurrentPosition)));
        sb_video.setProgress(mCurrentPosition);
        mVideoView.seekTo(mCurrentPosition);
        mVideoView.start();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    private void initView() {
       mLl_loading = (LinearLayout) findViewById(R.id.ll_loading);
        mLl_loading.setVisibility(View.GONE);
        mVideoView = (VideoView)findViewById(R.id.video_view);
        ll_top_ctrl = (LinearLayout) findViewById(R.id.ll_top_ctrl);
        ll_bottom_ctrl = (LinearLayout) findViewById(R.id.ll_bottom_ctrl);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_system_time = (TextView) findViewById(R.id.tv_system_time);
        tv_current_position = (TextView) findViewById(R.id.tv_current_position);
        tv_duration = (TextView) findViewById(R.id.tv_duration);
        iv_battery = (ImageView) findViewById(R.id.iv_battery);
        sb_voice = (SeekBar) findViewById(R.id.sb_voice);
        sb_video = (SeekBar) findViewById(R.id.sb_video);
        btn_pre = (Button) findViewById(R.id.btn_pre);
        btn_play = (Button) findViewById(R.id.btn_play);
        btn_next = (Button) findViewById(R.id.btn_next);
        mBtn_exit = (Button) findViewById(R.id.btn_exit);
        btn_voice = (Button) findViewById(R.id.btn_voice);
    }
    private void initCtrlLayout() {
        ll_top_ctrl.measure(0, 0);
        float translationY = ll_top_ctrl.getMeasuredHeight();
        ViewHelper.setTranslationY(ll_top_ctrl, -translationY);
        ll_bottom_ctrl.measure(0, 0);
        translationY = ll_bottom_ctrl.getMeasuredHeight();
        ViewHelper.setTranslationY(ll_bottom_ctrl, translationY);
    }
    private void registerBatterChangedReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int level = intent.getIntExtra("level", 0);
                uPdateBatteryBg(level);
                context.unregisterReceiver(this);
            }
        }, filter);
    }
    private void uPdateBatteryBg(final int level) {

        int resid;
        if (level == 0) {
            resid = R.drawable.ic_battery_0;
        } else if (level <= 10) {
            resid = R.drawable.ic_battery_10;
        } else if (level <= 20) {
            resid = R.drawable.ic_battery_20;
        } else if (level <= 40) {
            resid = R.drawable.ic_battery_40;
        } else if (level <= 60) {
            resid = R.drawable.ic_battery_60;
        } else if (level <= 80) {
            resid = R.drawable.ic_battery_80;
        } else {
            resid = R.drawable.ic_battery_100;
        }
        iv_battery.setBackgroundResource(resid);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UPDATE_CURRENT_POSITION:
                    updateCurrentPosition();
                    break;
                case HIDE_CTRL_LAYOUT:
                    toggleCtrlLayout();
                    break;
                default:
                    break;
            }
        }
    };
    private void toggleCtrlLayout() {
        float translationY = ViewHelper.getTranslationY(ll_top_ctrl);
        if (translationY == 0) {
            com.nineoldandroids.view.ViewPropertyAnimator.animate(ll_top_ctrl).translationY(-ll_top_ctrl.getHeight());
            ViewPropertyAnimator.animate(ll_bottom_ctrl).translationY(ll_bottom_ctrl.getHeight());
        } else {
            ViewPropertyAnimator.animate(ll_top_ctrl).translationY(0f);
            ViewPropertyAnimator.animate(ll_bottom_ctrl).translationY(0f);
        }
    }
    private void updateCurrentPosition() {

        mCurrentVolume = mAudioManger.getStreamVolume(AudioManager.STREAM_MUSIC);
        sb_voice.setProgress(mCurrentVolume);
        tv_system_time.setText(DateFormat.format("kk:mm:ss", System.currentTimeMillis()));
        mCurrentPosition = mVideoView.getCurrentPosition();
        tv_current_position.setText((Utils.formatMillis(mCurrentPosition)));
        sb_video.setProgress(mCurrentPosition);
        handler.sendEmptyMessageDelayed(UPDATE_CURRENT_POSITION, 300);
            }


    private void initLIstener() {
        mBtn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.pause();
                finish();
            }
        });
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                } else {
                    mVideoView.start();
                }
                updatePlayBtnBg();
            }
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                toggleCtrlLayout();
                return true;
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView.seekTo(0);
                tv_current_position.setText(Utils.formatMillis(0));
                btn_play.setBackgroundResource(R.drawable.btn_play_normal);
            }
        });
        sb_voice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    setStreamVolume(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        sb_video.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mVideoView.seekTo(seekBar.getProgress());
            }
        });
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
                int duration = mVideoView.getDuration();
                tv_duration.setText(Utils.formatMillis(duration));
                sb_video.setMax(duration);
                if (currentVideoItem != null) {
                    tv_title.setText(currentVideoItem.getTitle());
                }
                updateCurrentPosition();
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                } else {
                    mVideoView.start();
                }
                updatePlayBtnBg();
            }
        });
        btn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition > 0) {
                    currentPosition--;
                    openvideo();
                }
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition != videoItems.size() - 1) {
                    currentPosition++;
                    openvideo();
                }
            }
        });
        mBtn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
        btn_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAudioManger.getStreamVolume(AudioManager.STREAM_MUSIC) > 0) {
                    mCurrentVolume = mAudioManger.getStreamVolume(AudioManager.STREAM_MUSIC);
                    setStreamVolume(0);
                    sb_voice.setProgress(0);
                } else {
                    setStreamVolume(mCurrentVolume);
                    sb_voice.setProgress(mCurrentVolume);
                }
            }
        });
        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        mLl_loading.setVisibility(View.VISIBLE);
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        hideLoading();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        mVideoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                updateSecondaryProgress(percent);
            }
            private void updateSecondaryProgress(int percent) {
                float percentFloat = percent / 100f;
                int secondaryProgress = (int) (mVideoView.getDuration() * percentFloat);
                sb_video.setSecondaryProgress(secondaryProgress);
            }
        });
    }
    private void hideLoading ( ) {
        ViewPropertyAnimator.animate(mLl_loading).alpha(0.0f).setDuration(1500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart (Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                mLl_loading.setVisibility(View.GONE);
                ViewHelper.setAlpha(mLl_loading, 1.0f);
            }
            @Override
            public void onAnimationCancel (Animator animation) {
            }
            @Override
            public void onAnimationRepeat (Animator animation) {
            }
        });
    }
    private void updatePlayBtnBg() {
        int resid;
        if (mVideoView.isPlaying()) {
            resid = R.drawable.selector_btn_pause;
        } else {
            resid = R.drawable.selector_btn_play;
        }
        btn_play.setBackgroundResource(resid);
    }
    private void setStreamVolume(int index) {
        int streamType = AudioManager.STREAM_MUSIC;
        int flags = 0;
        mAudioManger.setStreamVolume(streamType, index, flags);
    }
    private void initData() {
        initVolume();
        String videoname=(String)getIntent().getSerializableExtra("videoname");
        Uri videoUri = getIntent().getData();
        if (videoUri != null) {
            mVideoView.setVideoURI(videoUri);
            tv_title.setText(videoname);
            btn_pre.setEnabled(false);
            btn_next.setEnabled(false);
            mBtn_exit.setEnabled(false);
            btn_play.setBackgroundResource(R.drawable.btn_pause_normal);
        } else {
            videoItems = (ArrayList<VideoItem>) getIntent().getSerializableExtra(Keys.ITEM_LIST);
            currentPosition = getIntent().getIntExtra(Keys.CURRENT_POSITION, -1);
            openvideo();
        }
    }
    private void initVolume() {
        mAudioManger = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = mAudioManger.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mCurrentVolume = mAudioManger.getStreamVolume(AudioManager.STREAM_MUSIC);
        sb_voice.setMax(maxVolume);
        sb_voice.setProgress(mCurrentVolume);
    }
    private void openvideo() {
        if (videoItems == null || videoItems.isEmpty() || currentPosition == -1) {
            return;
        }
        isPlaying=true;
        Intent intent=new Intent(this,AudioPlayerActivityOnGigle.class);
        intent.putExtra("stop",isPlaying);
        btn_play.setBackgroundResource(R.drawable.btn_pause_normal);
        btn_pre.setEnabled(currentPosition != 0);
        btn_next.setEnabled(currentPosition != videoItems.size() - 1);
        currentVideoItem = videoItems.get(currentPosition);
        if (mTitle==currentVideoItem.getTitle()){
            mVideoView.seekTo(mMcurrentPosition);
            mVideoView.start();
        }
        else {
            mVideoView.setVideoPath(currentVideoItem.getPath());
            mVideoView.start();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                removeHideCtrlLayoutMessage();
                break;
            case MotionEvent.ACTION_UP:
                sendHideCtrlLayoutMessage();
                break;
        }
        return super.onTouchEvent(event);
    }
    private void sendHideCtrlLayoutMessage() {
        removeHideCtrlLayoutMessage();
        handler.sendEmptyMessageDelayed(HIDE_CTRL_LAYOUT, 5000);
    }
    private void removeHideCtrlLayoutMessage() {
        handler.removeMessages(HIDE_CTRL_LAYOUT);
    }

    @Override
    protected void onPause() {
        sp.edit().putInt(Keys.POSITION_VIDEO_CURRENT_POSITION, mVideoView.getCurrentPosition()).apply();
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        handler.removeMessages(UPDATE_CURRENT_POSITION );

    }

    @Override
    protected void onDestroy() {
        System.out.println(".............. onDestroy..................");
        SystemApp systemApp=(SystemApp) getApplication();
        systemApp.delActivity(this);
        super.onDestroy();
    }
}
