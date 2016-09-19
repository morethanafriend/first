package com.example.administrator.my_shoujiyingyin.interfaces;/*
 * @创建者   2016/8/31.
 * @创建时间
 * @描述   2016/8/31.
 *
 * @更新者   2016/8/31.
 * @更新时间   2016/8/31.
 * @更新描述   2016/8/31.
 */

import com.example.administrator.my_shoujiyingyin.bean.AudioItem;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_ablum_single;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_single;

public interface PlayService {

    void openAudio();

    boolean isPlaying();

    void start();


    void pause();


    void pre();


    void next();


    void seekTo(int position);


    int getCurrentPosition();


    int getDuration();


    int switchPlayMode();


    int getCurrentPlayMode();


    AudioItem getCurrentAudioItem();

    VidelJsonBean_single.single_Video getmMCurrentsingle_Video ( );
    VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List getmMCurrentablum_single_Video ( );

}

