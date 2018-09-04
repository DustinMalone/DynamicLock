package com.example.mylock.DynamicLock.constants;


import com.example.mylock.DynamicLock.utils.FileUtil;

/**
 * 用于存放所有常量的类
 */
public class MyConstants {
    public static final String Lock= "Lock";//app的存放数据文件夹

    public static final String APPURL = "http://113.108.150.50:82/pmp/";//app的主要url

    public static final String FIRST_START = "firststart";//第一次启动

    public static final String Version = "version";//版本号


    public static final String getAppVersion = "app/APPInfo.do";//获取APP版本信息

    //路径常量
    public static final String SDCARD_PATH = FileUtil.getSDCardPath();// sd卡路径

}
