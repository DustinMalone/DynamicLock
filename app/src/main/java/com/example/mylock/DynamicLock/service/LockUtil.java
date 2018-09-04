package com.example.mylock.DynamicLock.service;

/**
 * Created by Administrator on 2017/5/2.
 */

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;

import static android.os.Build.VERSION_CODES.N;

/**
 * 锁屏工具类
 * @author tongleer.com
 *
 */
public class LockUtil {
    private Context mContext;
    private WindowManager mWindowManager;// 窗口管理器
    private View mLockView;// 锁屏视图
    private WindowManager.LayoutParams wmParams;
    private static LockUtil mLockUtil;
    private boolean isLocked;// 是否锁定
    public static synchronized LockUtil getInstance(Context mContext) {
        if (mLockUtil == null) {
            mLockUtil = new LockUtil(mContext);
        }
        return mLockUtil;
    }
    /**
     * 构造方法
     * @param mContext
     */
    public LockUtil(Context mContext) {
        this.mContext = mContext;
        init();
    }
    /**
     * 初始化布局
     */
    private void init() {
        isLocked = false;
        // 获取WindowManager
        mWindowManager = (WindowManager) mContext.getApplicationContext().getSystemService("window");
        // 设置LayoutParams(全局变量）相关参数
        wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;// 关键部分
        wmParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
        // 设置Window flag
        wmParams.width = WindowManager.LayoutParams.FILL_PARENT;
        wmParams.height = WindowManager.LayoutParams.FILL_PARENT;
        wmParams.flags = 1280;
    }
    /**
     * 锁屏
     */
    public synchronized void lock() {
        if (mLockView != null && !isLocked) {
            mWindowManager.addView(mLockView, wmParams);
        }
        isLocked = true;
    }
    /**
     * 解锁
     */
    public synchronized void unlock() {
        if (mWindowManager != null && isLocked) {
            mWindowManager.removeView(mLockView);
        }
        isLocked = false;
    }
    /**
     * 更新
     */
    public synchronized void update() {
        if (mLockView != null && !isLocked) {
            mWindowManager.updateViewLayout(mLockView, wmParams);
        }
    }
    /**
     * 设置锁屏视图
     * @param v
     */
    public synchronized void setLockView(View v) {
        mLockView = v;
    }
}