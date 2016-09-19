package com.example.administrator.my_shoujiyingyin.utils;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.example.administrator.my_shoujiyingyin.service.AudioPlayService;

/**
 * Created by Administrator on 2016/9/17.
 */
public class PhoneListener extends PhoneStateListener {
    AudioPlayService audioPlayService=new AudioPlayService();
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state){
            case TelephonyManager.CALL_STATE_RINGING:
                audioPlayService.pause();
            case TelephonyManager.CALL_STATE_IDLE:
                audioPlayService.start();
        }
    }
}
