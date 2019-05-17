package com.example.mylock.DynamicLock.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2017/5/2.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("BootReceiver","do....................");
//         context.startService(new Intent(context,LockService.class));//此Service可在首次启动Activity中启动
    }
}