package com.example.administrator.my_shoujiyingyin.service;/*
 * @创建者   2016/9/1.
 * @创建时间
 * @描述   2016/9/1.
 *
 * @更新者   2016/9/1.
 * @更新时间   2016/9/1.
 * @更新描述   2016/9/1.
 */

import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_single;

public interface PlayServiceOnLine {
    void openAudio ( );

    boolean isPlaying ( );

    void start ( );


    void pause ( );


    void pre ( );


    void next ( );


    void seekTo (int position);


    int getCurrentPosition ( );


    int getDuration ( );


    int switchPlayMode ( );


    int getCurrentPlayMode ( );

    VidelJsonBean_single.single_Video getmMCurrentposition0 ( );
}