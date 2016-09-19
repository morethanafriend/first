//package com.example.administrator.my_shoujiyingyin.service;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//import android.os.Messenger;
//import android.os.RemoteException;
//import android.preference.PreferenceManager;
//import android.support.v4.app.NotificationCompat;
//import android.widget.RemoteViews;
//
//import com.example.administrator.my_shoujiyingyin.R;
//import com.example.administrator.my_shoujiyingyin.activity.AudioPlayerActivity;
//import com.example.administrator.my_shoujiyingyin.bean.AudioItem;
//import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_single;
//import com.example.administrator.my_shoujiyingyin.interfaces.Keys;
//import com.example.administrator.my_shoujiyingyin.interfaces.PlayService;
//import com.example.administrator.my_shoujiyingyin.interfaces.Ui;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
///**
// * Created by Administrator on 2016/9/12.
// */
//public class ServiceExample extends Service implements PlayService {
//
//    public static final int UI_INTERFACE = 0;
//
//    public static final int PLAY_SERVICE_INTERFACE = 1;
//    private MediaPlayer mMediaPlayer;
//
//    private Ui ui;
//
//    public static final int PLAY_MODE_ORDER = 1;
//
//    public static final int PLAY_MODE_RANDOM = 2;
//
//    public static final int PLAY_MODE_SINGLE = 3;
//
//    public        int currentPlayMode = PLAY_MODE_ORDER;
//    public static int NO_OPEN_AUDIO   = 1;
//    public  int               openAudioFlag;
//    private SharedPreferences sp;
//
//    private static final int NOTIFICATION_PRE = 1;
//
//    private static final int NOTIFICATION_NEXT = 2;
//
//    private static final int NOTIFICATION_ROOT = 3;
//    private static final int NOTIFICATION_PASE = 4;
//    private              int notificationId    = 1;
//    private int    currentPosition;
//    private Random random;
//    private Handler handler = new Handler() {
//        public void handleMessage (android.os.Message msg) {
//            switch (msg.what) {
//                case UI_INTERFACE:
//                    ui = (Ui) msg.obj;
//                    Messenger uiMessenger = msg.replyTo;
//                    Message message = new Message();
//                    message.what = PLAY_SERVICE_INTERFACE;
//                    message.obj = ServiceExample.this;
//                    message.arg1 = openAudioFlag;
//                    try {
//                        uiMessenger.send(message);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//
//                default:
//                    break;
//            }
//        }
//
//        ;
//    };
//    private AudioItem           mCurrentAudioItem;
//    private NotificationManager notificationManager;
//    private Messenger playServiceMessenger = new Messenger(handler);
//    private ArrayList<AudioItem>                                              audioItems;
//    private int                                                               mCurrentPositionTemp;
//    private int                                                               mCurrentPositionTemponline;
//    private ArrayList<VidelJsonBean_single.single_Video>                      audioItemsonline;
//    private String                                                            mURL;
//    private VidelJsonBean_single.single_Video.single_Video_auditionList       mMCurrentposition0;
//    private List<VidelJsonBean_single.single_Video.single_Video_auditionList> mData;
//    private VidelJsonBean_single.single_Video                                 mData1;
//
//    @Override
//    public void onCreate ( ) {
//        sp = PreferenceManager.getDefaultSharedPreferences(this);
//        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        random = new Random();
//        super.onCreate();
//    }
//
//    @Override
//    public int onStartCommand (Intent intent, int flags, int startId) {
//        currentPlayMode = sp.getInt(Keys.CURRENT_PLAY_MODE, PLAY_MODE_ORDER);
//        openAudioFlag = -1;
//        int what = intent.getIntExtra(Keys.WHAT, -1);
//        switch (what) {
//            case NOTIFICATION_PRE:
//                pre();
//                break;
//            case NOTIFICATION_NEXT:
//                next();
//                break;
//            case NOTIFICATION_PASE:
//                if (mMediaPlayer.isPlaying()) {
//                    mMediaPlayer.pause();
//                } else {
//                    mMediaPlayer.start();
//                }
//                break;
//            case NOTIFICATION_ROOT:
//                openAudioFlag = NO_OPEN_AUDIO;
//                break;
//            default:
//                audioItemsonline = (ArrayList<VidelJsonBean_single.single_Video>)intent.getSerializableExtra(Keys.ITEM);
//                mCurrentPositionTemp = intent.getIntExtra(Keys.POSITION, -1);
//                audioItems = (ArrayList<AudioItem>) intent.getSerializableExtra(Keys.ITEM_LIST);
//                mCurrentPositionTemponline = intent.getIntExtra(Keys.CURRENT_POSITION, -1);
//                if (isPlaying() && currentPosition == mCurrentPositionTemp) {
//                    openAudioFlag = NO_OPEN_AUDIO;
//                }
//                currentPosition = mCurrentPositionTemp;
//                break;
//        }
//        return super.onStartCommand(intent, flags, startId);
//    }
//    @Override
//    public IBinder onBind (Intent intent) {
//        return  playServiceMessenger.getBinder();
//    }
//
//    @Override
//    public void openAudio ( ) {
//        if (audioItems == null || audioItems.isEmpty() || currentPosition == -1) {
//            return;
//        }
//        mCurrentAudioItem = audioItems.get(currentPosition);
//        if (mMediaPlayer != null) {
//            mMediaPlayer.reset();
//            mMediaPlayer.release();
//            mMediaPlayer = null;
//        }
//
//        try {
//            mMediaPlayer = new MediaPlayer();
//            mMediaPlayer.setDataSource(this, Uri.parse(mCurrentAudioItem.getPath()));
//            mMediaPlayer.prepareAsync();
//            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared (MediaPlayer mp) {
//                    start();
//                    ui.updateUI(mCurrentAudioItem);
//                }
//            });
//            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion (MediaPlayer mp) {
//                    next();
//                }
//            });
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    @Override
//    public boolean onUnbind (Intent intent) {
//        return super.onUnbind(intent);
//    }
//
//    @Override
//    public boolean isPlaying ( ) {
//        if (mMediaPlayer != null) {
//            return mMediaPlayer.isPlaying();
//        }
//        return false;
//    }
//
//    @Override
//    public void start ( ) {
//        if (mMediaPlayer != null) {
//            mMediaPlayer.start();
//            sendNotification();
//        }
//
//    }
//
//
//    private void sendNotification ( ) {
//        notificationManager.cancel(notificationId);
//        int                        icon          = R.drawable.ic_launcher;
//        CharSequence               tickerText    = "��ǰ���ڲ��ţ�" + mCurrentAudioItem.getTitle();
//        long                       when          = System.currentTimeMillis();
//        CharSequence               contentTitle  =  mCurrentAudioItem.getTitle();
//        CharSequence               contentText   = mCurrentAudioItem.getArtist();
//        PendingIntent              contentIntent = getActivityPndingIntent(NOTIFICATION_ROOT);
//        NotificationCompat.Builder builder       = new NotificationCompat.Builder(this);
//        builder.setSmallIcon(icon)
//                .setTicker(tickerText)
//                .setWhen(when)
//                .setContentTitle(contentTitle)
//                .setContentText(contentText)
//                .setContentIntent(contentIntent)
//                .setContent(getRemoteViews());
//        Notification notification = builder.build();
//        notificationManager.notify(notificationId, notification);
//    }
//
//    private RemoteViews getRemoteViews ( ) {
//        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
//        remoteViews.setTextViewText(R.id.tv_title, mCurrentAudioItem.getTitle());
//        remoteViews.setTextViewText(R.id.tv_artist, mCurrentAudioItem.getArtist());
//        remoteViews.setOnClickPendingIntent(R.id.btn_pre, getServicePndingIntent(NOTIFICATION_PRE));
//        //        remoteViews.setOnClickPendingIntent(R.id.btn_pause, getServicePndingIntent(NOTIFICATION_PASE));
//        remoteViews.setOnClickPendingIntent(R.id.btn_next, getServicePndingIntent(NOTIFICATION_NEXT));
//        remoteViews.setOnClickPendingIntent(R.id.ll_root, getActivityPndingIntent(NOTIFICATION_ROOT));
//        return remoteViews;
//    }
//
//    private PendingIntent getServicePndingIntent (int notificationPre) {
//        Intent intent = new Intent(this, AudioPlayService.class);
//        intent.putExtra(Keys.WHAT, notificationPre);
//        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
//        PendingIntent contentIntent = PendingIntent.getService(this, notificationPre, intent, flags);
//        return contentIntent;
//    }
//
//    private PendingIntent getActivityPndingIntent (int notificationRoot) {
//        Intent intent = new Intent(this, AudioPlayerActivity.class);
//        intent.putExtra(Keys.WHAT, notificationRoot);
//        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
//        PendingIntent contentIntent = PendingIntent.getActivity(this, notificationRoot, intent, flags);
//        return contentIntent;
//    }
//
//
//    @Override
//    public void pause ( ) {
//        if (mMediaPlayer != null) {
//            mMediaPlayer.pause();
//            notificationManager.cancel(notificationId);
//        }
//    }
//
//    @Override
//    public void pre ( ) {
//        switch (currentPlayMode) {
//            case PLAY_MODE_ORDER:
//                if (currentPosition != 0) {
//                    currentPosition--;
//                } else {
//                    currentPosition = audioItems.size() - 1;
//                }
//                break;
//            case PLAY_MODE_RANDOM:
//                currentPosition = random.nextInt(audioItems.size());
//                break;
//            case PLAY_MODE_SINGLE:
//                break;
//        }
//
//        openAudio();
//
//
//    }
//
//    @Override
//    public void next ( ) {
//        switch (currentPlayMode) {
//            case PLAY_MODE_ORDER:
//                if (currentPosition != audioItems.size() - 1) {
//                    currentPosition++;
//                } else {
//                    currentPosition = 0;
//                }
//                break;
//            case PLAY_MODE_RANDOM:
//                currentPosition = random.nextInt(audioItems.size());
//                break;
//            case PLAY_MODE_SINGLE:
//                break;
//        }
//
//        openAudio();
//
//    }
//
//    @Override
//    public void seekTo (int position) {
//        if (mMediaPlayer != null) {
//            mMediaPlayer.seekTo(position);
//        }
//    }
//
//    @Override
//    public int getCurrentPosition ( ) {
//        if (mMediaPlayer != null) {
//            return mMediaPlayer.getCurrentPosition();
//        }
//        return 0;
//    }
//
//    @Override
//    public int getDuration ( ) {
//        if (mMediaPlayer != null) {
//            return mMediaPlayer.getDuration();
//        }
//        return 0;
//
//    }
//    @Override
//    public int switchPlayMode ( ) {
//        switch (currentPlayMode) {
//            case PLAY_MODE_ORDER:
//                currentPlayMode = PLAY_MODE_SINGLE;
//                break;
//            case PLAY_MODE_SINGLE:
//                currentPlayMode = PLAY_MODE_RANDOM;
//                break;
//            case PLAY_MODE_RANDOM:
//                currentPlayMode = PLAY_MODE_ORDER;
//                break;
//            default:
//                throw new RuntimeException("见鬼了吧，谁知道谁是谁啊,currentPlayMode = " + currentPlayMode);
//        }
//        sp.edit().putInt(Keys.CURRENT_PLAY_MODE, currentPlayMode).commit();
//
//        return currentPlayMode;
//    }
//
//    @Override
//    public int getCurrentPlayMode ( ) {
//        return currentPlayMode;
//    }
//
//    @Override
//    public AudioItem getCurrentAudioItem ( ) {
//        return mCurrentAudioItem;
//    }
//}