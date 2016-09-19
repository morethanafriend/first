package com.example.administrator.my_shoujiyingyin.service;/*
 * @创建者   2016/9/1.
 * @创建时间
 * @描述   2016/9/1.
 *
 * @更新者   2016/9/1.
 * @更新时间   2016/9/1.
 * @更新描述   2016/9/1.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.administrator.my_shoujiyingyin.activity.AudioPlayerActivityOnGigle;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_single;
import com.example.administrator.my_shoujiyingyin.interfaces.Keys;
import com.example.administrator.my_shoujiyingyin.interfaces.Uionline;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AudioPlayServiceOnGigle extends Service implements PlayServiceOnLine {
    public static final int UI_INTERFACE = 0;
    public static final int PLAY_SERVICE_INTERFACE = 1;
    private MediaPlayer mMediaPlayer;
    private Uionline    ui;
    public static final int PLAY_MODE_ORDER = 1;
    public static final int PLAY_MODE_RANDOM = 2;
    public static final int PLAY_MODE_SINGLE = 3;
    public        int currentPlayMode = PLAY_MODE_ORDER;
    public static int NO_OPEN_AUDIO   = 1;
    public  int               openAudioFlag;
    private SharedPreferences sp;
    private static final int NOTIFICATION_PRE = 1;
    private static final int NOTIFICATION_NEXT = 2;
    private static final int NOTIFICATION_ROOT = 3;
    private static final int NOTIFICATION_PASE = 4;
    private              int notificationId    = 1;
    private int    currentPosition;
    private Random random;
    private Handler handler = new Handler() {
        public void handleMessage (android.os.Message msg) {
            switch (msg.what) {
                case UI_INTERFACE:
                    ui = (Uionline) msg.obj;
                    Messenger uiMessenger = msg.replyTo;
                    Message message = new Message();
                    message.what = PLAY_SERVICE_INTERFACE;
                    message.obj = AudioPlayServiceOnGigle.this;
                    message.arg1 = openAudioFlag;
                    System.out.println("...........umessage.arg1 = openAudioFlag...................");
                    try {
                        uiMessenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
        }
    };
    private NotificationManager notificationManager;
    private Messenger playServiceMessenger = new Messenger(handler);
    private int                  mCurrentPositionTemp;
    private ArrayList<VidelJsonBean_single.single_Video> audioItems;
    private String mURL;
    private VidelJsonBean_single.single_Video.single_Video_auditionList mMCurrentposition0;
    private List<VidelJsonBean_single.single_Video.single_Video_auditionList> mData;
    private VidelJsonBean_single.single_Video mData1;

    @Override
    public void onCreate ( ) {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        random = new Random();
        super.onCreate();
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
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
            case NOTIFICATION_PASE:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                } else {
                    mMediaPlayer.start();
                }
                break;
            case NOTIFICATION_ROOT:
                openAudioFlag = NO_OPEN_AUDIO;
                break;
            default:
                audioItems = (ArrayList<VidelJsonBean_single.single_Video>)intent.getSerializableExtra(Keys.ITEM);
                mCurrentPositionTemp = intent.getIntExtra(Keys.POSITION, -1);
                if (isPlaying() && currentPosition == mCurrentPositionTemp) {
                    openAudioFlag = NO_OPEN_AUDIO;
                }
                currentPosition = mCurrentPositionTemp;
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public IBinder onBind (Intent intent) {
        return  playServiceMessenger.getBinder();
    }

    @Override
    public void openAudio() {

        mData = audioItems.get(currentPosition).auditionList;
        mData1 = audioItems.get(currentPosition);
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        try {
            mURL = mData.get(0).url;
            mMediaPlayer = MediaPlayer.create(AudioPlayServiceOnGigle.this, Uri.parse(mURL));
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion (MediaPlayer mp) {
                    switch (currentPlayMode) {
                        case PLAY_MODE_ORDER:
                            if (currentPosition != audioItems.size() - 1) {
                                currentPosition++;
                            } else {
                                currentPosition = 0;
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
                public void onPrepared (MediaPlayer mp) {
                    start();
                    ui.updateUI(mData1);
                }
            });
            mMediaPlayer.start();
        } catch (Exception e) {
            Toast toast = new Toast(AudioPlayServiceOnGigle.this);
            View layoutView = LayoutInflater.from(AudioPlayServiceOnGigle.this).inflate(R.layout.toast2, null);
            toast.setView(layoutView);
            toast.setGravity(Gravity.BOTTOM | Gravity.CLIP_HORIZONTAL, -50, 80);
            toast.setDuration(Toast.LENGTH_SHORT);// 设置消息的显示持续时间
            toast.show();// 显示吐司
            return;

        }
    }

    @Override
    public boolean onUnbind (Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public boolean isPlaying ( ) {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public void start ( ) {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            sendNotification();
        }

    }

    private void sendNotification ( ) {
        notificationManager.cancel(notificationId);
        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "��ǰ���ڲ��ţ�" + audioItems.get(currentPosition).name;
        long when = System.currentTimeMillis();
        CharSequence contentTitle =  mData1.name;
        CharSequence contentText = audioItems.get(currentPosition).singerName;
        PendingIntent contentIntent = getActivityPndingIntent(NOTIFICATION_ROOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(icon)
                .setTicker(tickerText)
                .setWhen(when)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setContentIntent(contentIntent)
                .setContent(getRemoteViews());
        Notification notification = builder.build();
        notificationManager.notify(notificationId, notification);
    }

    private RemoteViews getRemoteViews ( ) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.tv_title,  audioItems.get(currentPosition).name);
        remoteViews.setTextViewText(R.id.tv_artist, audioItems.get(currentPosition).singerName);
        remoteViews.setOnClickPendingIntent(R.id.btn_pre, getServicePndingIntent(NOTIFICATION_PRE));
        remoteViews.setOnClickPendingIntent(R.id.btn_next, getServicePndingIntent(NOTIFICATION_NEXT));
        remoteViews.setOnClickPendingIntent(R.id.ll_root, getActivityPndingIntent(NOTIFICATION_ROOT));
        return remoteViews;
    }

    private PendingIntent getServicePndingIntent (int notificationPre) {
        Intent intent = new Intent(this, AudioPlayServiceOnGigle.class);
        intent.putExtra(Keys.WHAT, notificationPre);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        PendingIntent contentIntent = PendingIntent.getService(this, notificationPre, intent, flags);
        return contentIntent;
    }

    private PendingIntent getActivityPndingIntent (int notificationRoot) {
        System.out.println("...........notificationRoot......................"+notificationRoot);
        Intent intent = new Intent(this, AudioPlayerActivityOnGigle.class);
        intent.putExtra(Keys.WHAT, notificationRoot);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        PendingIntent contentIntent = PendingIntent.getActivity(this, notificationRoot, intent, flags);
        return contentIntent;
    }


    @Override
    public void pause ( ) {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            notificationManager.cancel(notificationId);
        }
    }

    @Override
    public void pre ( ) {
        switch (currentPlayMode) {
            case PLAY_MODE_ORDER:
                if (currentPosition != 0) {
                    currentPosition--;
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

    @Override
    public void next ( ) {
        switch (currentPlayMode) {
            case PLAY_MODE_ORDER:
                if (currentPosition != audioItems.size() - 1) {
                    currentPosition++;
                } else {
                    currentPosition = 0;
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

    @Override
    public void seekTo (int position) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(position);
        }
    }

    @Override
    public int getCurrentPosition ( ) {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public int getDuration ( ) {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getDuration();
        }
        return 0;

    }
    @Override
    public int switchPlayMode ( ) {
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
        sp.edit().putInt(Keys.CURRENT_PLAY_MODE, currentPlayMode).commit();

        return currentPlayMode;
    }

    @Override
    public int getCurrentPlayMode ( ) {
        return currentPlayMode;
    }

    @Override
    public VidelJsonBean_single.single_Video getmMCurrentposition0(){
        return mData1;
    }
}
