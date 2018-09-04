package com.example.mylock.DynamicLock.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.example.mylock.DynamicLock.Activity.LockActivity;

/**
 * 锁屏Service，用于监听屏幕变亮或变暗事件
 * @author tongleer.com
 *
 */
public class LockService extends Service{
    private ScreenOnReceiver screenOnReceiver;
    private ScreenOffReceiver screenOffReceiver;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("servicecreate","do....................");
        screenOnReceiver=new ScreenOnReceiver();
        IntentFilter screenOnFilter=new IntentFilter();
        screenOnFilter.addAction("android.intent.action.SCREEN_ON");
        registerReceiver(screenOnReceiver, screenOnFilter);
        screenOffReceiver=new ScreenOffReceiver();
        IntentFilter screenOffFilter=new IntentFilter();
        screenOffFilter.addAction("android.intent.action.SCREEN_OFF");
        registerReceiver(screenOffReceiver, screenOffFilter);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("onStartCommand","do....................");
        return Service.START_STICKY;
    }
    /**
     * 监听屏幕变亮的广播接收器，变亮就屏蔽系统锁屏
     * @author tongleer.com
     *
     */
    private class ScreenOnReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals("android.intent.action.SCREEN_ON")){
                    /*
                     * 此方式已经过时，在activtiy中编写
                     * getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                     * getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                     * 两句可以代替此方式
                     */
                    /*KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
                    KeyguardLock lock = keyguardManager.newKeyguardLock(“”);
                    lock.disableKeyguard();*/
            }
        }
    }
    /**
     * 监听屏幕变暗的广播接收器，变暗就启动应用锁屏界面activity
     * @author tongleer.com
     *
     */
    private class ScreenOffReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals("android.intent.action.SCREEN_OFF")){
                Log.e("ScreenOffReceiver","do....................");
                Intent i1=new Intent(context,LockActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i1);
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenOnReceiver);
        unregisterReceiver(screenOffReceiver);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}