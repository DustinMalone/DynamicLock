package com.example.mylock.DynamicLock.Activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.mylock.DynamicLock.R;
import com.example.mylock.DynamicLock.base.BaseActivity;
import com.example.mylock.DynamicLock.service.LockService;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.tv_log)
    private TextView tv_log;
    @ViewInject(R.id.btn_lock)
    private ToggleButton btn_lock;

    @Override
    protected void init(Bundle savedInstanceState) {
        Log.e("asdasdasdas", "ddddddddddddddddddddddddd");
        btn_lock.setChecked(isServiceWork(mContext, "com.example.mylock.DynamicLock.service" +
                ".LockService"));

        if (btn_lock.isChecked()) {

            tv_log.setText("开启中");
        } else {

            tv_log.setText("已关闭");
        }

        setTitle("萨芬撒123123");

        btn_lock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tv_log.setText("开启中?");
                    Log.e("log", "eeeeeeeeeeeeeeeeeeeeeeeeee");
                    mContext.startService(new Intent(mContext, LockService.class));

                } else {
                    tv_log.setText("已关闭?");
                    Log.e("log", "eeeeeeeeeeeeeeeeeeeeeeeeee");
//                    mContext.stopService(new Intent(mContext,LockService.class));
                }
            }
        });
    }

    @Override
    protected void ObjectMessage(Message msg) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_push:
                startActivity(new Intent(this,PlayActivity.class));
                break;
        }

    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
