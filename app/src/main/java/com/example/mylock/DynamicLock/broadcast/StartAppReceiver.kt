package com.example.mylock.DynamicLock.broadcast

import android.app.KeyguardManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import com.example.mylock.DynamicLock.Activity.ShowImageActivity


/**
 * Created by LCB on 2018/11/9.
 */
class StartAppReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        //开机后一般会停留在锁屏页面且短时间内没有进行解锁操作屏幕会进入休眠状态，此时就需要先唤醒屏幕和解锁屏幕
//        屏幕唤醒
        var pm = context!!.getSystemService(Context.POWER_SERVICE) as PowerManager;
        var wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_DIM_WAKE_LOCK, "StartupReceiver");//最后的参数是LogCat里用的Tag
        wl.acquire();
//        屏幕解锁
        var km = context!!.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager;
        var kl = km.newKeyguardLock("StartupReceiver");//参数是LogCat里用的Tag
        kl.disableKeyguard();
        if (intent!!.action == "android.intent.action.BOOT_COMPLETED") {
            Log.e("TAG", "onReceive: 开机启动");
            //开机启动
            var mainIntent = Intent(context, ShowImageActivity::class.java);
            //在BroadcastReceiver中显示Activity，必须要设置FLAG_ACTIVITY_NEW_TASK标志
            mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            context.startActivity(mainIntent);
        }
    }
}