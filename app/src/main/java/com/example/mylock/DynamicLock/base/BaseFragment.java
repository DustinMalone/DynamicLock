package com.example.mylock.DynamicLock.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.mylock.DynamicLock.utils.HttpTools;
import com.example.mylock.DynamicLock.utils.Utils;

import org.xutils.x;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;



/**
 * Created by Administrator on 2016/8/23 0023.
 */
public abstract  class BaseFragment extends Fragment{

    private static final int HTTP_ERROR = 999;
    private InputMethodManager inputMethodManager;
    private Context mContext;
    protected View baseView;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       baseView= x.view().inject(this,inflater,container);
        mContext=getActivity();
        init();
        return baseView;
    }


    /**
     * 初始化
     */
    protected abstract void init();


    /**
     * 隐藏键盘
     */
    public void unkeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(
                        ((Activity) mContext).getCurrentFocus()
                                .getWindowToken(), 0);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * 重新加载。留给子类类重写，可重写，可不重写，提供统一的名字的重新加载设置界面的方法
     */
    public void reinit() {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 提示框
     */
    public void SToast(String message) {
        synchronized (getActivity()) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ObjectMessage(msg);
            //     dismissLoadingDialog();
            super.handleMessage(msg);

        }
    };

    /**
     * Handler 处理事件 HTTP_ERROR：999 请求网络失败返回处理
     */
    protected abstract void ObjectMessage(Message msg);


    /**
     * post请求 date 请求内容 what handler 消息处理
     */
    public void HttpPost ( final String url, final String data,final int what){
        if (!Utils.GetNetState(mContext)){
            Message message=handler.obtainMessage();
            message.what=HTTP_ERROR;
            message.obj=what;
            handler.sendMessage(message);
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=handler.obtainMessage();
                try {
                    message.obj= HttpTools.GetContentByPost(url,data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                message.what=what;
                handler.sendMessage(message);
            }
        }).start();
        
    }

    /**
     * post请求 map 用hashmap来放数据 请求内容 what handler 消息处理
     */

    public void HttpPost(final String url, final HashMap<String,String>hashMap , final int what){
        if (!Utils.GetNetState(mContext)){
            Message message=handler.obtainMessage();
            message.what=HTTP_ERROR;
            message.obj=what;
            handler.sendMessage(message);
            return;
        }
        Set set=hashMap.entrySet();
        Iterator iterator=set.iterator();
        String data="";
        while (iterator.hasNext()){
            Map.Entry<String,String> entry= (Map.Entry<String, String>) iterator.next();
            data=data+"&"+entry.getKey()+"="+entry.getValue();
        }
       data=data.replaceFirst("&","");
        final String postData=data;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=handler.obtainMessage();
                try {
                    message.obj=HttpTools.GetContentByPost(url,postData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                message.what=what;
                handler.sendMessage(message);
            }
        }).start();
    }


}
