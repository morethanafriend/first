package com.example.administrator.my_shoujiyingyin.activity;/*
 * @创建者   2016/8/22.
 * @创建时间
 * @描述   2016/8/22.
 *
 * @更新者   2016/8/22.
 * @更新时间   2016/8/22.
 * @更新描述   2016/8/22.
 */

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_single;
import com.example.administrator.my_shoujiyingyin.interfaces.Keys;
import com.example.administrator.my_shoujiyingyin.interfaces.Uionline;
import com.example.administrator.my_shoujiyingyin.manager.LyricView;
import com.example.administrator.my_shoujiyingyin.manager.SystemApp;
import com.example.administrator.my_shoujiyingyin.service.AudioPlayService;
import com.example.administrator.my_shoujiyingyin.service.AudioPlayServiceOnGigle;
import com.example.administrator.my_shoujiyingyin.service.PlayServiceOnLine;
import com.example.administrator.my_shoujiyingyin.utils.Utils;

import java.util.ArrayList;

public class AudioPlayerActivityOnGigle extends AppCompatActivity implements Uionline {
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
    private TextView             mTv_play_time;
    private static final int UPDATE_PLAY_TIME = 0;
    private Button            mBtn_back;
    private Button            mBtn_selector_lyric;
    private int               switchLyricView;
    private PlayServiceOnLine playService;
    private static final int       Lyric_View_Visible   =11;
    private static final int       Lyric_View_Unvisible =12;
    private              Handler   handler              = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case AudioPlayService.PLAY_SERVICE_INTERFACE:
                    playService = (PlayServiceOnLine) msg.obj;
                    if (msg.arg1 == AudioPlayService.NO_OPEN_AUDIO) {
                        updateUI(playService.getmMCurrentposition0());
                    } else {
                        playService.openAudio();
                    }
                    break;
                case UPDATE_PLAY_TIME:
                    updatePlayTime();
                    break;

                default:
                    break;
            }
        };
    };
    private              Messenger uiMessenger          = new Messenger(handler);
    private ServiceConnection conn;
    private ArrayList<VidelJsonBean_single.single_Video> mAblum_single_listss;
    private int mPositiononline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_audio_player);
        SystemApp systemApp=(SystemApp) getApplication();//Application对象 应用是单例的
        systemApp.addStack(this);
        initView();
        initData();
        initListener();
    }
    public void initView() {
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
    public void initListener() {
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
                playService.seekTo(seekBar.getProgress());
            }
        });
        mBtn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playService.pre();
            }
        });
        mBtn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playService.next();
            }
        });
        mBtn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resid;
                if (playService.isPlaying()) {
                    playService.pause();
                    resid = R.drawable.selector_audio_btn_pause;
                } else {
                    playService.start();
                    resid = R.drawable.selector_audio_btn_play;
                }
                mBtn_play.setBackgroundResource(resid);
            }
        });
        mSb_audio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    playService.seekTo(progress);
                }
            }
        });
    }
    private void switchLyricView ( ) {
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
                throw new RuntimeException("谁知道谁是谁啊,currentPlayMode  " );
        }
    }

    public void initData() {
        connectServcie();
    }
    private void connectServcie() {
        mAblum_single_listss = (ArrayList<VidelJsonBean_single.single_Video>)getIntent()
                .getSerializableExtra(Keys.ITEM);
        System.out.println("...........mAblum_single_listss....................."+mAblum_single_listss);
        mPositiononline = getIntent().getIntExtra(Keys.POSITION, -1);
        System.out.println("...........mPositiononline......................"+mPositiononline);
        Intent service = new Intent(this, AudioPlayServiceOnGigle.class);
        service.putExtra(Keys.ITEM, mAblum_single_listss);
        service.putExtra(Keys.POSITION, mPositiononline);
        service.putExtra(Keys.WHAT, getIntent().getIntExtra(Keys.WHAT, -1));
        startService(service);
        conn = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Messenger playServiceMesseger = new Messenger(binder);
                Message message = new Message();
                message.what = AudioPlayService.UI_INTERFACE;
                message.obj = AudioPlayerActivityOnGigle.this;
                message.replyTo = uiMessenger;
                System.out.println("...........message.replyTo......................"+message.replyTo);
                try {
                    playServiceMesseger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
        bindService(service, conn, BIND_AUTO_CREATE);
    }
    @Override
    protected void onDestroy() {
        unbindService(conn);
        handler.removeCallbacksAndMessages(null);
        SystemApp systemApp=(SystemApp) getApplication();
        systemApp.delActivity(this);
        super.onDestroy();
    }
    private void switchPlayMode() {
        int currentPlayMode = playService.switchPlayMode();
        updatePlayModeBtnBg(currentPlayMode);
    }
    private void updatePlayModeBtnBg(int currentPlayMode) {
        int resid;
        switch (currentPlayMode) {
            case AudioPlayService.PLAY_MODE_ORDER:
                resid = R.drawable.selector_audio_btn_playmode_order;
                break;
            case AudioPlayService.PLAY_MODE_SINGLE:
                resid = R.drawable.selector_audio_btn_playmode_single;
                break;
            case AudioPlayService.PLAY_MODE_RANDOM:
                resid = R.drawable.selector_audio_btn_playmode_random;
                break;
            default:
                throw new RuntimeException("见鬼了，谁知道谁是谁,currentPlayMode = " + currentPlayMode);
        }
        mBtn_play_mode.setBackgroundResource(resid);
    }
    private void play() {
        if (playService.isPlaying()) {
            playService.pause();
        } else {
            playService.start();
        }

        updatePlayBtnBg();
    }
    private void updatePlayBtnBg() {
        int resid;
        if (playService.isPlaying()) {
            resid = R.drawable.selector_audio_btn_pause;
        } else {
            resid = R.drawable.selector_audio_btn_play;
        }
        mBtn_play.setBackgroundResource(resid);
    }


    public void updateUI(VidelJsonBean_single.single_Video mData1) {
        updatePlayBtnBg();
        mBtn_play.setBackgroundResource(R.drawable.btn_audio_play_normal);
        mTv_title.setText(mData1.name);
        mTv_artist.setText(mData1.singerName);
        mSb_audio.setMax(playService.getDuration());
        updatePlayTime();
        updatePlayModeBtnBg(playService.getCurrentPlayMode());
    }
    private void updatePlayTime() {
        int position = playService.getCurrentPosition();
        CharSequence currentPosition = Utils.formatMillis(position);
        CharSequence duration = Utils.formatMillis(playService.getDuration());
        mTv_play_time.setText(currentPosition + "/" + duration);
        mSb_audio.setProgress(position);
        handler.sendEmptyMessageDelayed(UPDATE_PLAY_TIME, 30);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,MainMainActivity.class);
        startActivity(intent);
    }
}




