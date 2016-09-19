package com.example.administrator.my_shoujiyingyin.service;/*
 * @创建者   2016/8/31.
 * @创建时间
 * @描述   2016/8/31.
 *
 * @更新者   2016/8/31.
 * @更新时间   2016/8/31.
 * @更新描述   2016/8/31.
 */


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.activity.AudioPlayerActivity;
import com.example.administrator.my_shoujiyingyin.bean.AudioItem;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_ablum_single;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_single;
import com.example.administrator.my_shoujiyingyin.interfaces.Keys;
import com.example.administrator.my_shoujiyingyin.interfaces.PlayService;
import com.example.administrator.my_shoujiyingyin.interfaces.Ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AudioPlayService extends Service  implements PlayService {
    private ArrayList<VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List>               mAblum_single_listss_true;
    private ArrayList<VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List.Audition_List> mData2;
    private VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List                          mData3;
    private List<VidelJsonBean_single.single_Video.single_Video_auditionList>                mData;
    private VidelJsonBean_single.single_Video                                                mData1;
    private String                                                                           mURL;
    private int                                                                              mSinglePositiononline;
    public static final int UI_INTERFACE_SINGLE           = 2;
    public static final int UI_INTERFACE                  = 0;
    public static final int PLAY_SERVICE_INTERFACE_ONLINE = 3;
    public static final int PLAY_SERVICE_INTERFACE        = 1;
    private MediaPlayer mMediaPlayer;
    private Ui          ui;
    public static final int PLAY_MODE_ORDER             = 1;
    public static final int PLAY_MODE_RANDOM            = 2;
    public static final int PLAY_MODE_SINGLE            = 3;
    public              int currentPlayMode             = PLAY_MODE_ORDER;
    public static       int NO_OPEN_AUDIO_ONLINE_SINGLE = 3;
    public static       int NO_OPEN_AUDIO_ONLINE        = 2;
    public static       int NO_OPEN_AUDIO               = 1;
    public  int               openAudioFlag;
    private SharedPreferences sp;
    private static final int NOTIFICATION_PRE   = 1;
    private static final int NOTIFICATION_NEXT  = 2;
    private static final int NOTIFICATION_ROOT  = 3;
    private static final int NOTIFICATION_PAUSE = 4;
    private              int notificationId     = 1;
    private int    currentPosition;
    private Random random;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UI_INTERFACE:
                    ui = (Ui) msg.obj;
                    Messenger uiMessenger = msg.replyTo;
                    Message message = new Message();
                    message.what = PLAY_SERVICE_INTERFACE;
                    message.obj = AudioPlayService.this;
                    message.arg1 = openAudioFlag;
                    try {
                        uiMessenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case UI_INTERFACE_SINGLE:
                    ui = (Ui) msg.obj;
                    Messenger uiMessenger1 = msg.replyTo;
                    Message message1 = new Message();
                    message1.what = PLAY_SERVICE_INTERFACE_ONLINE;
                    message1.obj = AudioPlayService.this;
                    message1.arg1 = openAudioFlag;
                    try {
                        uiMessenger1.send(message1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };
    private AudioItem           mCurrentAudioItem;
    private NotificationManager notificationManager;
    private Messenger playServiceMessenger = new Messenger(handler);
    private ArrayList<AudioItem>                         audioItems;
    private int                                          mCurrentPositionTempOnLine;
    private int                                          mCurrentPositionTemp;
    private int                                          currentPositionOnLine;
    private int                                          mIcon;
    private CharSequence                                 mTickerText;
    private long                                         mWhen;
    private CharSequence                                 mContentTitle;
    private CharSequence                                 mContentText;
    private ArrayList<VidelJsonBean_single.single_Video> mAblum_single_listss;
    private AudioManager                                 mAudioManager;

    @Override
    public void onCreate() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        random = new Random();
        registerIntentReceiver();
        mAudioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        super.onCreate();
        int result = mAudioManager.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        if (!mMediaPlayer.isPlaying()) {
                            mMediaPlayer.start();
                        }
                        mMediaPlayer.setVolume(1.0f, 1.0f);
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        if (mMediaPlayer.isPlaying())
                            ;
                        mMediaPlayer.stop();
                        mMediaPlayer.release();
                        mMediaPlayer = null;
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        if (mMediaPlayer.isPlaying())
                            ;
                        mMediaPlayer.pause();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        if (mMediaPlayer.isPlaying()) {
                            mMediaPlayer.setVolume(0.1f, 0.1f);
                        }
                        break;
                }
            }
        }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    private void registerIntentReceiver() {
         new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action == "com.example.administrator.my_shoujiyingyin.service.AudioPlayService.pause") {
                    pause();

                } else if (action == "com.example.administrator.my_shoujiyingyin.service.AudioPlayService.start") {
                    start();

                }
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        currentPlayMode = sp.getInt(Keys.CURRENT_PLAY_MODE, PLAY_MODE_ORDER);
        openAudioFlag = -1;
        int what = intent.getIntExtra(Keys.WHAT, -1);
        switch (what) {
            case NOTIFICATION_PRE:
                pre();
                break;
            case NOTIFICATION_NEXT:
                next();
                break;
            case NOTIFICATION_PAUSE:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                } else {
                    mMediaPlayer.start();
                }
                break;
            case NOTIFICATION_ROOT:
                if (audioItems != null) {
                    openAudioFlag = NO_OPEN_AUDIO;
                } else if (mAblum_single_listss != null) {
                    openAudioFlag = NO_OPEN_AUDIO_ONLINE;
                } else if (mAblum_single_listss_true != null) {
                    openAudioFlag = NO_OPEN_AUDIO_ONLINE_SINGLE;
                }
                break;
            default:
                mAblum_single_listss = (ArrayList<VidelJsonBean_single.single_Video>) intent.getSerializableExtra(Keys.ITEM);
                mCurrentPositionTempOnLine = intent.getIntExtra(Keys.POSITION, -1);
                audioItems = (ArrayList<AudioItem>) intent.getSerializableExtra(Keys.ITEM_LIST);
                mCurrentPositionTemp = intent.getIntExtra(Keys.CURRENT_POSITION, -1);
                mAblum_single_listss_true = (ArrayList<VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List>) intent.getSerializableExtra(Keys.ITEM_LIST_SINGLE);
                mSinglePositiononline = intent.getIntExtra(Keys.POSITION_SINGLE, -1);
                if (isPlaying() && currentPosition == mCurrentPositionTemp) {
                    openAudioFlag = NO_OPEN_AUDIO;
                }
                if (audioItems != null) {
                    if (isPlaying() && currentPosition == mCurrentPositionTemp) {
                        openAudioFlag = NO_OPEN_AUDIO;
                    }
                    currentPosition = mCurrentPositionTemp;
                } else if (mAblum_single_listss != null) {
                    if (isPlaying() && currentPosition == mCurrentPositionTempOnLine) {
                        openAudioFlag = NO_OPEN_AUDIO_ONLINE;
                    }
                    currentPosition = mCurrentPositionTempOnLine;
                } else if (mAblum_single_listss_true != null) {
                    if (isPlaying() && currentPosition == mSinglePositiononline) {
                        openAudioFlag = NO_OPEN_AUDIO_ONLINE_SINGLE;
                    }
                    currentPosition = mSinglePositiononline;
                }
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return playServiceMessenger.getBinder();
    }

    @Override
    public void openAudio() {
        if (mAblum_single_listss_true != null) {
            if (mMediaPlayer != null) {
                mMediaPlayer.reset();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
            mData2 = mAblum_single_listss_true.get(currentPosition).auditionList;
            mData3 = mAblum_single_listss_true.get(currentPosition);
            try {
                mURL = mData2.get(0).url;
                mMediaPlayer = MediaPlayer.create(AudioPlayService.this, Uri.parse(mURL));
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        switch (currentPlayMode) {
                            case PLAY_MODE_ORDER:
                                if (currentPosition != mAblum_single_listss_true.size() - 1) {
                                    currentPosition++;
                                } else {
                                    currentPosition = mAblum_single_listss_true.size() - 1;
                                }
                                break;
                            case PLAY_MODE_RANDOM:
                                currentPosition = random.nextInt(mAblum_single_listss_true.size());
                                break;
                            case PLAY_MODE_SINGLE:
                                break;
                        }
                        openAudio();
                    }
                });
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        start();
                        ui.updateUIAblumeonline(mData3);
                    }
                });
            } catch (Exception e) {
                Toast toast      = new Toast(AudioPlayService.this);
                View  layoutView = LayoutInflater.from(AudioPlayService.this).inflate(R.layout.toast2, null);
                toast.setView(layoutView);
                toast.setGravity(Gravity.BOTTOM | Gravity.CLIP_HORIZONTAL, -50, 80);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
        } else if (mAblum_single_listss != null) {
            if (mMediaPlayer != null) {
                mMediaPlayer.reset();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
            mData = mAblum_single_listss.get(currentPosition).auditionList;
            mData1 = mAblum_single_listss.get(currentPosition);
            try {
                mURL = mData.get(0).url;
                mMediaPlayer = MediaPlayer.create(AudioPlayService.this, Uri.parse(mURL));
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        switch (currentPlayMode) {
                            case PLAY_MODE_ORDER:
                                if (currentPosition != mAblum_single_listss.size() - 1) {
                                    currentPosition++;
                                } else {
                                    currentPosition = mAblum_single_listss.size() - 1;
                                }
                                break;
                            case PLAY_MODE_RANDOM:
                                currentPosition = random.nextInt(mAblum_single_listss.size());
                                break;
                            case PLAY_MODE_SINGLE:
                                break;
                        }
                        openAudio();
                    }
                });
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        start();
                        ui.updateUIonline(mData1);
                    }
                });
            } catch (Exception e) {
                Toast toast      = new Toast(AudioPlayService.this);
                View  layoutView = LayoutInflater.from(AudioPlayService.this).inflate(R.layout.toast2, null);
                toast.setView(layoutView);
                toast.setGravity(Gravity.BOTTOM | Gravity.CLIP_HORIZONTAL, -50, 80);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

        } else if (audioItems != null) {
            if (mMediaPlayer != null) {
                mMediaPlayer.reset();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
            mCurrentAudioItem = audioItems.get(currentPosition);
            try {
                mMediaPlayer = MediaPlayer.create(this, Uri.parse(mCurrentAudioItem.getPath()));
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        switch (currentPlayMode) {
                            case PLAY_MODE_ORDER:
                                if (currentPosition != audioItems.size() - 1) {
                                    currentPosition++;
                                } else {
                                    currentPosition = audioItems.size() - 1;
                                }
                                break;
                            case PLAY_MODE_RANDOM:
                                currentPosition = random.nextInt(audioItems.size());
                                break;
                            case PLAY_MODE_SINGLE:
                                break;
                        }
                        openAudio();
                    }
                });
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        start();
                        ui.updateUI(mCurrentAudioItem);
                    }
                });


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public void start() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            sendNotification();
        }

    }


    private void sendNotification() {
        notificationManager.cancel(notificationId);
        if (audioItems != null) {
            mIcon = R.drawable.ic_launcher;
            mTickerText = "��ǰ���ڲ��ţ�" + mCurrentAudioItem.getTitle();
            mWhen = System.currentTimeMillis();
            mContentTitle = mCurrentAudioItem.getTitle();
            mContentText = mCurrentAudioItem.getArtist();
        } else if (mAblum_single_listss != null) {
            mIcon = R.drawable.ic_launcher;
            mTickerText = "��ǰ���ڲ��ţ�" + mAblum_single_listss.get(currentPosition).name;
            mWhen = System.currentTimeMillis();
            mContentTitle = mData1.name;
            mContentText = mAblum_single_listss.get(currentPosition).singerName;
        } else if (mAblum_single_listss_true != null) {
            mIcon = R.drawable.ic_launcher;
            mTickerText = "��ǰ���ڲ��ţ�" + mAblum_single_listss_true.get(currentPosition).name;
            mWhen = System.currentTimeMillis();
            mContentTitle = mData3.name;
            mContentText = mData3.singerName;
        }
        PendingIntent              contentIntent = getActivityPndingIntent(NOTIFICATION_ROOT);
        NotificationCompat.Builder builder       = new NotificationCompat.Builder(this);
        builder.setSmallIcon(mIcon)
                .setTicker(mTickerText)
                .setWhen(mWhen)
                .setContentTitle(mContentTitle)
                .setContentText(mContentText)
                .setContentIntent(contentIntent)
                .setContent(getRemoteViews());
        Notification notification = builder.build();
        notificationManager.notify(notificationId, notification);
    }

    private RemoteViews getRemoteViews() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
        if (audioItems != null) {
            remoteViews.setTextViewText(R.id.tv_title, mCurrentAudioItem.getTitle());
            remoteViews.setTextViewText(R.id.tv_artist, mCurrentAudioItem.getArtist());
        } else if (mAblum_single_listss != null) {
            remoteViews.setTextViewText(R.id.tv_title, mAblum_single_listss.get(currentPosition).name);
            remoteViews.setTextViewText(R.id.tv_artist, mAblum_single_listss.get(currentPosition).singerName);
        } else if (mAblum_single_listss_true != null) {
            remoteViews.setTextViewText(R.id.tv_title, mAblum_single_listss_true.get(currentPosition).name);
            remoteViews.setTextViewText(R.id.tv_artist, mAblum_single_listss_true.get(currentPosition).singerName);
        }
        remoteViews.setOnClickPendingIntent(R.id.btn_pre, getServicePndingIntent(NOTIFICATION_PRE));
        remoteViews.setOnClickPendingIntent(R.id.btn_pause, getServicePndingIntent(NOTIFICATION_PAUSE));
        remoteViews.setOnClickPendingIntent(R.id.btn_next, getServicePndingIntent(NOTIFICATION_NEXT));
        remoteViews.setOnClickPendingIntent(R.id.ll_root, getActivityPndingIntent(NOTIFICATION_ROOT));
        return remoteViews;
    }

    private PendingIntent getServicePndingIntent(int notificationPre) {
        Intent intent = new Intent(this, AudioPlayService.class);
        intent.putExtra(Keys.WHAT, notificationPre);
        int           flags         = PendingIntent.FLAG_UPDATE_CURRENT;
        PendingIntent contentIntent = PendingIntent.getService(this, notificationPre, intent, flags);
        return contentIntent;
    }

    private PendingIntent getActivityPndingIntent(int notificationRoot) {
        Intent intent = new Intent(this, AudioPlayerActivity.class);
        intent.putExtra(Keys.WHAT, notificationRoot);
        intent.putExtra(Keys.ITEM, mAblum_single_listss);
        intent.putExtra(Keys.POSITION, currentPosition);
        intent.putExtra(Keys.ITEM_LIST, audioItems);
        intent.putExtra(Keys.CURRENT_POSITION, currentPosition);
        intent.putExtra(Keys.ITEM_LIST_SINGLE, mAblum_single_listss_true);
        intent.putExtra(Keys.POSITION_SINGLE, currentPosition);
        int           flags         = PendingIntent.FLAG_UPDATE_CURRENT;
        PendingIntent contentIntent = PendingIntent.getActivity(this, notificationRoot, intent, flags);
        return contentIntent;
    }

    @Override
    public void pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            notificationManager.cancel(notificationId);
        }
    }

    @Override
    public void pre() {
        switch (currentPlayMode) {
            case PLAY_MODE_ORDER:
                if (currentPosition != 0) {
                    currentPosition--;
                } else {
                    if (audioItems != null) {
                        currentPosition = audioItems.size() - 1;
                    } else if (mAblum_single_listss != null) {
                        currentPosition = mAblum_single_listss.size() - 1;
                    } else if (mAblum_single_listss_true != null) {
                        currentPosition = mAblum_single_listss_true.size() - 1;
                    }
                }
                break;
            case PLAY_MODE_RANDOM:
                if (audioItems != null) {
                    currentPosition = random.nextInt(audioItems.size());
                } else if (mAblum_single_listss != null) {
                    currentPosition = random.nextInt(mAblum_single_listss.size());
                } else if (mAblum_single_listss_true != null) {
                    currentPosition = random.nextInt(mAblum_single_listss_true.size());
                }
                break;
            case PLAY_MODE_SINGLE:
                break;
        }

        openAudio();


    }

    @Override
    public void next() {
        switch (currentPlayMode) {
            case PLAY_MODE_ORDER:
                if (audioItems != null) {
                    if (currentPosition != audioItems.size() - 1) {
                        currentPosition++;
                    } else {
                        currentPosition = 0;
                    }
                } else if (mAblum_single_listss != null) {
                    if (currentPosition != mAblum_single_listss.size() - 1) {
                        currentPosition++;
                    } else {
                        currentPosition = 0;
                    }
                } else if (mAblum_single_listss_true != null) {
                    if (currentPosition != mAblum_single_listss_true.size() - 1) {
                        currentPosition++;
                    } else {
                        currentPosition = 0;
                    }
                }
                break;
            case PLAY_MODE_RANDOM:
                if (audioItems != null) {
                    currentPosition = random.nextInt(audioItems.size());
                } else if (mAblum_single_listss != null) {
                    currentPosition = random.nextInt(mAblum_single_listss.size());
                } else if (mAblum_single_listss_true != null) {
                    currentPosition = random.nextInt(mAblum_single_listss_true.size());
                }
                break;
            case PLAY_MODE_SINGLE:
                break;
        }
        openAudio();

    }

    @Override
    public void seekTo(int position) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(position);
        }
    }

    @Override
    public int getCurrentPosition() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public int getDuration() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getDuration();
        }
        return 0;

    }

    @Override
    public int switchPlayMode() {
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
                throw new RuntimeException("见鬼了吧，谁知道谁是谁啊,currentPlayMode = " + currentPlayMode);
        }
        sp.edit().putInt(Keys.CURRENT_PLAY_MODE, currentPlayMode).apply();

        return currentPlayMode;
    }

    @Override
    public int getCurrentPlayMode() {
        return currentPlayMode;
    }

    @Override
    public AudioItem getCurrentAudioItem() {
        return mCurrentAudioItem;
    }

    @Override
    public VidelJsonBean_single.single_Video getmMCurrentsingle_Video() {
        return mData1;
    }

    @Override
    public VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List getmMCurrentablum_single_Video() {
        return mData3;
    }
}

