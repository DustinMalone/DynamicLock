package com.example.mylock.DynamicLock.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylock.DynamicLock.R;
import com.example.mylock.DynamicLock.config.AppManager;
import com.example.mylock.DynamicLock.config.MyApplication;
import com.example.mylock.DynamicLock.constants.MyConstants;
import com.example.mylock.DynamicLock.utils.HttpTools;
import com.example.mylock.DynamicLock.utils.Utils;

import org.xutils.x;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;



/**
 * Created by Administrator on 2016/8/15 0015.
 */
public abstract class BaseActivity extends Activity implements OnClickListener {

    protected MyApplication myApplication;
    protected Context mContext = this;
    private Toast toast;
    //判断activity是否已经销毁
    private boolean isDestroy = false;

    protected final static int HTTP_ERROR = 999;

    protected int IsHttpOK = 0; // 0 失败 1 成功
    //加载框
    protected ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext = this;
        myApplication = (MyApplication) getApplication();
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        // 去除头部
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        x.view().inject(this);
        //初始化
        init(savedInstanceState);

        toast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);//初始化一个toast，解决多次弹出toast冲突问题
    }

    /**
     * @param savedInstanceState
     * @return void
     * @description: 主要用于控件等初始化工作
     */
    protected abstract void init(Bundle savedInstanceState);


    /**
     * 设置标题
     */
    public void setTitle(int id) {
        ((TextView) findViewById(R.id.title)).setText(getResources().getString(id));
    }

    /**
     * 设置标题
     */
    public void setTitle(String s) {
        ((TextView) findViewById(R.id.title)).setText(s);
    }

    /**
     * 设置右侧标题
     */
    public void setRight(String text) {
        ((TextView) findViewById(R.id.right)).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.right)).setText(text);
        ((TextView) findViewById(R.id.right)).setOnClickListener(this);
    }

    public void setRight(int imgResource){
//        ((ImageView)findViewById(R.id.right)).setVisibility(View.VISIBLE);
//        ((ImageView)findViewById(R.id.right)).setOnClickListener(this);
//        ((ImageView)findViewById(R.id.right)).setImageResource(imgResource);
    }


    /**
     * 重写setContentView，让子类传入的View上方再覆盖一层LoadingView
     */
    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(mContext).inflate(layoutResID, null);
        super.setContentView(view);
    }

    /**
     * 居中提示框
     */
    public void CToast(String message) {
        synchronized (mContext) {
            toast.cancel();
            toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 底部提示框
     */
    public void SToast(String message) {
        synchronized (mContext) {
            toast.cancel();
            toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * 底部提示框
     */
    public void SToast(int id) {
        synchronized (mContext) {
            toast.cancel();
            toast = Toast.makeText(mContext, getResources().getString(id), Toast.LENGTH_SHORT);
            toast.show();
            //    Toast.makeText(mContext, getResources().getString(id), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 自定义布局提示框
     */
    public void SToast(View view) {
        synchronized (mContext) {
            toast.cancel();
            LinearLayout.LayoutParams patams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            Toast toast = new Toast(mContext);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setLayoutParams(patams);
            linearLayout.setBackgroundResource(R.drawable.transla_dialog_bg);
            linearLayout.addView(view);
            toast.setView(linearLayout);
            toast.show();
        }
    }

    /**
     * @param message
     * 显示加载框
     */
    public void showProgressDialog(String message){
        if (null==progressDialog){
            progressDialog=new ProgressDialog(mContext);
        }
        progressDialog.setMessage(message);
        progressDialog.show();
        //设置是否按返回键dismiss
        progressDialog.setCancelable(false);
    }

    /**
     * 隐藏加载框
     */
    public void dismissProgressDialog(){
        if (progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    /**
     * @param flag
     * 设置是否按返回键来隐藏加载框
     */
    public void setDialogCancelable(Boolean flag) {
        if (progressDialog != null) {
            progressDialog.setCancelable(flag);
        }
    }

    /**
     * @param message 提示内容
     * @param cancel  取消的what
     * @param ok      确定的what
     */
//    public void getMyDialog(String message, final int cancel, final int ok) {
//        new AlertDialogUtils(mContext).builder()
//                .setTitle("提示")
//                .setMsg(message)
//                .setPositiveButton("确认", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        handler.sendEmptyMessage(ok);
//                    }
//                })
//                .setNegativeButton("取消", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        handler.sendEmptyMessage(cancel);
//                    }
//                }).show();
//
//
//    }

    /**
     * Handler 处理事件 HTTP_ERROR：999 请求网络失败返回处理
     */
    protected abstract void ObjectMessage(Message msg);


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (isDestroy) {
                return;
            }
            ObjectMessage(msg);
            // dismissLoadingDialog();
//            shapeLoadingDialog.dismiss();
            super.handleMessage(msg);

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroy = true;
//        shapeLoadingDialog.dismiss();
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().removeActivityFromStack(this);
    }


    /**
     * 判断网络请求是否有效
     */
    public void validStatusCode(final String url, final int what) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                IsHttpOK = HttpTools.validStatusCode(url);
                Message msg = handler.obtainMessage();
                msg.what = what;
                msg.obj = IsHttpOK + "";
                handler.sendMessage(msg);
            }
        }).start();
        // 网络
    }


    /**
     * post请求 map 用hashmap来放数据 请求内容 what handler 消息处理
     */
    public void HttpPostWitoutDialog(final String url, final HashMap<String, String> map, final int what) {
        if (!Utils.GetNetState(mContext)) {
            Message msg = handler.obtainMessage();
            msg.what = HTTP_ERROR;
            msg.obj = what;
            handler.sendMessage(msg);
            return;
        }
        Set set = map.keySet();
        Iterator iter = set.iterator();
        String data = "";
        String key;
        while (iter.hasNext()) {
            key = (String) iter.next();
            data = data + "&" + key + "=" + map.get(key);
        }
        data = data.replaceFirst("&", "");
        Log.w("提交数据data", data + "  ");
        final String finalData = data;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                String result = "";
                System.out.println(MyConstants.APPURL + url);
                try {
                    result = HttpTools.GetContentByPost(MyConstants.APPURL + url,
                            finalData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                msg.what = what;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }).start();

    }

    /**
     * post请求 map 用hashmap来放数据 请求内容 what handler 消息处理
     */
    public void HttpPost(final String url, final HashMap<String, String> map, final int what) {
        if (!Utils.GetNetState(mContext)) {
            Message msg = handler.obtainMessage();
            msg.what = HTTP_ERROR;
            msg.obj = what;
            handler.sendMessage(msg);
            return;
        }

        showProgressDialog("加载...");
        Set set = map.keySet();
        Iterator iter = set.iterator();
        String data = "";
        String key;
        while (iter.hasNext()) {
            key = (String) iter.next();
            data = data + "&" + key + "=" + map.get(key);
        }
        data = data.replaceFirst("&", "");
        Log.w("提交数据data", data + "  ");
        final String finalData = data;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                String result = "";
                System.out.println(MyConstants.APPURL + url);
                try {
                    result = HttpTools.GetContentByPost(MyConstants.APPURL + url,
                            finalData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                msg.what = what;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }).start();

    }


    /**
     * get请求 what handler 消息处理
     */
    public void HttpGet(final String url, final int what) {
        if (!Utils.GetNetState(mContext)) {
            Message msg = handler.obtainMessage();
            msg.what = HTTP_ERROR;
            msg.obj = what;
            handler.sendMessage(msg);
            return;
        }
        //   showLoadingDialog("加载...");
        showProgressDialog("加载...");
//        shapeLoadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
//                System.out.println(MyConstants.APPURL + url);
                String result = HttpTools.GetContent(MyConstants.APPURL + url);
                msg.what = what;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }).start();

    }

    /**
     * post请求 date 请求内容 what handler 消息处理
     */
    public void HttpPost(final String url, final String data, final int what) {
        if (!Utils.GetNetState(mContext)) {
            Message msg = handler.obtainMessage();
            msg.what = HTTP_ERROR;
            msg.obj = what;
            handler.sendMessage(msg);
            return;
        }
//          showLoadingDialog("加载...");
//        shapeLoadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();//初始化msg
                String result = "";
//                System.out.println(MyConstants.APPURL + url);
                try {
                    result = HttpTools.GetContentByPost(MyConstants.APPURL + url,
                            data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                msg.what = what;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }).start();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            dismissLoadingDialog();
            dismissProgressDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 提供给titbar 的back 放回事件
     */
    public void backfinish(View view) {
        unkeyboard();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    /**
     * 隐藏键盘
     */
    public void unkeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(
                        ((Activity) mContext).getCurrentFocus()
                                .getWindowToken(), 0);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
