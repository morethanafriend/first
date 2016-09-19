package com.example.administrator.my_shoujiyingyin.activity;/*
 * @创建者   2016/8/22.
 * @创建时间
 * @描述   2016/8/22.
 *
 * @更新者   2016/8/22.
 * @更新时间   2016/8/22.
 * @更新描述   2016/8/22.
 */

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.bean.AudioItem;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_ablum_single;
import com.example.administrator.my_shoujiyingyin.manager.LyricView;
import com.example.administrator.my_shoujiyingyin.manager.SystemApp;
import com.example.administrator.my_shoujiyingyin.utils.Utils;

import java.util.ArrayList;
import java.util.Random;

public class AudioPlayerActivityOnline extends AppCompatActivity {
    private AudioItem            currentAudioItems;
    private int                  mPositiononline;
    private Button               mBtn_play;
    private Button               mBtn_play_mode;
    private Button               mBtn_pre;
    private Button               mBtn_next;
    private TextView             mTv_title;
    private TextView             mTv_size;
    private TextView             mTv_artist;
    private SeekBar              mSb_audio;
    private ImageView            mIv_vision;
    private AnimationDrawable    mAnim;
    private LyricView            mLyric_view;
    private ArrayList<AudioItem> audioItems;
    private MediaPlayer          mediaPlayer;
    private Context              mContext;
    private int                  position;
    private TextView             mTv_play_time;
    private static final int Lyric_View_Visible=18;
    private static final int Lyric_View_Unvisible=19;
    private static final int UPDATE_CURREN_POSITION=20;
    private static final int PLAY_MODE_ORDER=21;
    private static final int PLAY_MODE_SINGLE=22;
    private static final int PLAY_MODE_RANDOM=23;
    private static final int UPDATE_CURREN_POSITION_ON_LINE=24;
    private Button                                                             mBtn_back;
    private Random                                                             mRandom;
    private int                                                                currentPlayMode;
    private Button                                                             mBtn_selector_lyric;
    private int                                                                switchLyricView;
    private String                                                             mURL;
    private ArrayList<VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List> mAblum_single_listss;
    private int                                                                mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_audio_player);
        SystemApp systemApp=(SystemApp) getApplication();//Application对象 应用是单例的
        systemApp.addStack(this);
        mContext = this;
        mRandom = new Random();
        initView();
        initData();
        initListener();

    }


    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_CURREN_POSITION_ON_LINE:
                    updateCurrentPositiononLine();
                    break;
                default:
                    break;
            }
        }
    };

    private void initListener() {
        mBtn_selector_lyric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLyricView();
            }
        });

        mBtn_play_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchPlayMode();
                updateBackgroundResource();
            }
        });
        mBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                finish();
            }
        });
        mSb_audio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });
        mBtn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentPlayMode) {
                    case PLAY_MODE_ORDER:
                        if (mPositiononline != 0) {
                            mPositiononline--;
                        } else {
                            mPositiononline = 0;
                        }
                        break;
                    case PLAY_MODE_RANDOM:
                        mPositiononline = mRandom.nextInt(mAblum_single_listss.size());
                        break;
                    case PLAY_MODE_SINGLE:
                        break;
                }
                openAudioOnLine();
            }

        });
        mBtn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    switch (currentPlayMode) {
                        case PLAY_MODE_ORDER:
                            if (mPositiononline != 0) {
                                mPositiononline++;
                            } else {
                                mPositiononline = 0;
                            }
                            break;
                        case PLAY_MODE_RANDOM:
                            mPositiononline =mRandom.nextInt(mAblum_single_listss.size());
                            break;
                        case PLAY_MODE_SINGLE:
                            break;
                    }

                    openAudioOnLine();
            }
        });
        mBtn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resid;
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    resid = R.drawable.selector_audio_btn_pause;
                } else {
                    mediaPlayer.start();
                    resid = R.drawable.selector_audio_btn_play;
                }
                mBtn_play.setBackgroundResource(resid);
            }
        });
    }

    private int switchLyricView() {
        switch (switchLyricView) {
            case Lyric_View_Visible:
                switchLyricView = Lyric_View_Unvisible;
                mBtn_selector_lyric.setBackgroundResource(R.drawable.btn_music_list_normal);
                mLyric_view.setVisibility(View.VISIBLE);
                break;
            case Lyric_View_Unvisible:
                switchLyricView = Lyric_View_Visible;
                mBtn_selector_lyric.setBackgroundResource(R.drawable.btn_music_list_pressed);
                mLyric_view.setVisibility(View.INVISIBLE);
                break;
            default:
                throw new RuntimeException("谁知道谁是谁啊,currentPlayMode = " + currentPlayMode);
        }
        return switchLyricView;

    }

    private void updateBackgroundResource() {
        int resid;
        switch (currentPlayMode){
            case PLAY_MODE_ORDER:
                resid = R.drawable.selector_audio_btn_playmode_order;
                break;
            case PLAY_MODE_SINGLE:
                resid = R.drawable.selector_audio_btn_playmode_single;
                break;
            case PLAY_MODE_RANDOM:
                resid = R.drawable.selector_audio_btn_playmode_random;
                break;
            default:
                throw new RuntimeException("谁知道谁是谁啊,currentPlayMode = " + currentPlayMode);
        } mBtn_play_mode.setBackgroundResource(resid);
    }

    private int switchPlayMode() {
        switch (currentPlayMode) {
            case PLAY_MODE_ORDER:
                currentPlayMode = PLAY_MODE_SINGLE;
                break;
            case PLAY_MODE_SINGLE:
                currentPlayMode = PLAY_MODE_RANDOM;
                break;
            case PLAY_MODE_RANDOM:
                currentPlayMode = PLAY_MODE_ORDER;
                break;
            default:
                throw new RuntimeException("谁知道谁是谁啊,currentPlayMode = " + currentPlayMode);
        }
        return currentPlayMode;

    }
    private void initData() {
        String picUrl=(String)getIntent().getSerializableExtra("picUrl");
        mAblum_single_listss = (ArrayList< VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List>)getIntent().getSerializableExtra("item");
            mPositiononline = (int)getIntent().getSerializableExtra("position");
            mLyric_view.setMusicPath(picUrl);

            currentPlayMode = PLAY_MODE_ORDER;
            mBtn_play.setBackgroundResource(R.drawable.btn_audio_play_normal);
            openAudioOnLine();
    }

    private void openAudioOnLine ( ) {
        release();
        mURL = mAblum_single_listss.get(mPositiononline).auditionList.get(0).url;
        mediaPlayer = MediaPlayer.create(mContext, Uri.parse(mURL));
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                switch (currentPlayMode) {
                    case PLAY_MODE_ORDER:
                        if (mPositiononline != mAblum_single_listss.size()-1) {
                            mPositiononline++;
                        } else {
                            mPositiononline = mAblum_single_listss.size() - 1;
                        }
                        break;
                    case PLAY_MODE_RANDOM:
                        mPositiononline =mRandom.nextInt(mAblum_single_listss.size());
                        break;
                    case PLAY_MODE_SINGLE:
                        break;
                }

                openAudioOnLine ();
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mBtn_selector_lyric.setClickable(false);
                mediaPlayer.start();
                updateCurrentPositiononLine ();
            }
        });
        mediaPlayer.start();

    }

    private void updateCurrentPositiononLine () {
        int duration=mediaPlayer.getDuration();
        mSb_audio.setMax(duration);
        if (mAblum_single_listss!=null){
            mTv_title.setText(mAblum_single_listss.get(mPositiononline).name);
            mTv_artist.setText(mAblum_single_listss.get(mPositiononline).singerName);}
        int currentPosition=mediaPlayer.getCurrentPosition();
        mSb_audio.setProgress(currentPosition);
        mTv_play_time.setText(Utils.formatMillis(currentPosition)+"/"+Utils.formatMillis(mediaPlayer.getDuration()));
        mLyric_view.setCurrentPosition(currentPosition);
        handler.sendEmptyMessageDelayed(UPDATE_CURREN_POSITION_ON_LINE,300);
    }
    private void initView() {
        mBtn_play = (Button) findViewById(R.id.btn_play);
        mBtn_play_mode = (Button) findViewById(R.id.btn_play_mode);
        mBtn_pre = (Button) findViewById(R.id.btn_pre);
        mBtn_next = (Button) findViewById(R.id.btn_next);
        mTv_title = (TextView) findViewById(R.id.tv_title);
        mTv_size = (TextView) findViewById(R.id.tv_size);
        mTv_artist = (TextView) findViewById(R.id.tv_artist);
        mSb_audio = (SeekBar) findViewById(R.id.sb_audio);
        mIv_vision = (ImageView) findViewById(R.id.iv_vision);
        mTv_play_time = (TextView)findViewById(R.id.tv_play_time);
        mAnim = (AnimationDrawable) mIv_vision.getBackground();
        mAnim.start();
        mLyric_view = (LyricView) findViewById(R.id.lyric_view);
        mBtn_back = (Button)findViewById(R.id.btn_back);
        mBtn_selector_lyric = (Button)findViewById(R.id.btn_selector_lyric);
    }

    private void release() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }
    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        SystemApp systemApp=(SystemApp) getApplication();
        systemApp.delActivity(this);
        super.onDestroy();
    }
}