package com.example.mylock.DynamicLock.config;

import android.app.Application;

import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.xutils.x;


/**
 * Created by Administrator on 2016/8/15 0015.
 */
public class MyApplication extends Application{

    private final static String TAG = "MyApplication";
    private ApplicationLike tinkerApplicationLike;
    private ZegoLiveRoom g_ZegoApi;
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化
        x.Ext.init(this);
        // 设置是否输出debug
        x.Ext.setDebug(true);

        //自动适应布局
        AutoLayoutConifg.getInstance().useDeviceSize();

        // 测试环境开关
//        ZegoLiveRoom.setTestEnv(true);
//
//        // 根据当前运行模式是否打开调试信息，仅供参考
//        ZegoLiveRoom.setVerbose(BuildConfig.DEBUG);
//
//        // 设置 UserID 和 UserName。userID 和 userName 来自于 App 自定义的账号系统
//        ZegoLiveRoom.setUser("0", "admin");
//
//        g_ZegoApi = new ZegoLiveRoom();
//        long appID = 1258711576;
//        byte[] signKey = {
//                (byte) 0x46, (byte) 0xb5,(byte)0x79,(byte)0x18, (byte) 0xc3, (byte) 0x89,
//                (byte) 0x77, (byte) 0xa7,(byte)0x0c, (byte) 0xf7,(byte)0x3c,
//                (byte) 0x8b, (byte) 0xac,
//                (byte) 0x58, (byte) 0xb7, (byte) 0xb0, (byte) 0xd2,
//                (byte)0x75, (byte) 0x97,(byte)0x7c, (byte) 0xf8,(byte)0x6c,
//                (byte)0x57,(byte)0x78, (byte) 0xd9,
//                (byte) 0x9a, (byte) 0x91,(byte)0x59,
//                (byte) 0xd9, (byte) 0x9a,(byte)0x6f, (byte) 0xf3
//        };
//        g_ZegoApi.setSDKContext(new ZegoSDKContext());
//        g_ZegoApi.initSDK(appID, signKey);

        // 我们可以从这里获得Tinker加载过程的信息
        tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

        // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
        TinkerPatch.init(tinkerApplicationLike)
                .reflectPatchLibrary()
                .setPatchRollbackOnScreenOff(true)
                .setPatchRestartOnSrceenOff(true)
                .setFetchPatchIntervalByHours(1);

        // 每隔3个小时(通过setFetchPatchIntervalByHours设置)去访问后台时候有更新,通过handler实现轮训的效果
        TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
    }

    private class ZegoSDKContext implements ZegoLiveRoom.SDKContext{

        @Override
        public String getSoFullPath() {
            return null;
        }

        @Override
        public String getLogPath() {
            return null;
        }

        @Override
        public Application getAppContext() {
            return (MyApplication)getApplicationContext();


        }
    }
}
