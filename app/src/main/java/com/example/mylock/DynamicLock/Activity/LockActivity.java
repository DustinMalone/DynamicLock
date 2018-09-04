package com.example.mylock.DynamicLock.Activity;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.mylock.DynamicLock.R;
import com.example.mylock.DynamicLock.base.BaseActivity;
import com.example.mylock.DynamicLock.service.LockUtil;

import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class LockActivity extends BaseActivity {

    @ViewInject(R.id.et_mima)
    private EditText et_mima;



    @Override
    protected void init(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//        setContentView(R.layout.activity_lock);

        View lockView = View.inflate(this, R.layout.activity_lock, null);
        LockUtil  lockLayer = new LockUtil(this);
        lockLayer.setLockView(lockView);// 设置要展示的页面
        lockLayer.lock();// 开启锁屏
//        CustomView.setHandler(mHandler);
    }

    @Override
    protected void ObjectMessage(Message msg) {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sumit :
                SimpleDateFormat formatter = new SimpleDateFormat ("HHmm");
                Date curDate = new Date(System.currentTimeMillis()-2*60*1000);//获取当前时间
                String str = formatter.format(curDate);
                if (et_mima.getText().toString().trim().equals(str.trim()))
                {
                    LockUtil.getInstance(mContext).unlock();
                }

                    break;
        }
    }
}
