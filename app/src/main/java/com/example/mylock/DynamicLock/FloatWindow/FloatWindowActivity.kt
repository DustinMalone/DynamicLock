package com.example.mylock.DynamicLock.FloatWindow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.mylock.DynamicLock.R
import com.yhao.floatwindow.FloatWindow
import com.yhao.floatwindow.MoveType
import com.yhao.floatwindow.Screen
import java.io.IOException
import java.io.OutputStream




class FloatWindowActivity : AppCompatActivity() {

    private var mShowFloat: Button? = null
    val TAG = "lkx"
    private var os: OutputStream? = null
    private var isShow: Boolean = false //悬浮窗口是否显示
    private var isOpen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_float_window)

        mShowFloat = findViewById(R.id.showFloat) as Button
        mShowFloat!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (isOpen) {
                    mShowFloat!!.setText("打开辅助")
                    FloatWindow.destroy("logo")
                } else {
                    mShowFloat!!.setText("关闭辅助")
                    showFloat()
                }
                isOpen = !isOpen
            }
        })

//        pathView.pathAnimator
//                .delay(100)
//                .duration(3000).interpolator(AccelerateDecelerateInterpolator())
//                .start()
//        pathView.setFillAfter(true);

//        svga.startAnimation()

    }

    //执行shell命令
    private fun exec(cmd: String) {
        try {
            if (os == null) {
                os = Runtime.getRuntime().exec("su").outputStream
            }
            os!!.write(cmd.toByteArray())
            os!!.flush()
        } catch (e: IOException) {
            Toast.makeText(this, "ROOT权限获取失败", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }

    }

    private fun showFloat() {
        val view = View.inflate(this, R.layout.float_view, null)
        val imageView = ImageView(this)
        imageView.setImageResource(R.mipmap.ic_launcher)
        imageView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (!isShow) {
                    Log.d(TAG, "onClick: 创建了")
                    FloatWindow
                            .with(applicationContext)
                            .setView(view)
                            .setWidth(Screen.width, 1f)
                            .setHeight(Screen.height, 0.5f)
                            .setX(100)
                            .setTag("window")
                            .setDesktopShow(true)
                            .setMoveType(MoveType.inactive)
                            .setY(Screen.height, 0.3f)
                            .build()
                    onResume()
                } else {
                    FloatWindow.get("window").hide()
                    FloatWindow.destroy("window")
                }
                isShow = !isShow
            }
        })

        showLogoFloat(imageView)
        view.setOnTouchListener(object : View.OnTouchListener {

            private var mStartY: Float = 0.toFloat()
            private var mStartX: Float = 0.toFloat()

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN //按下
                    -> {
                        Log.d(TAG, "开始位置: " + event.rawX + " " + event.rawY)
                        mStartX = event.rawX
                        mStartY = event.rawY
                    }
                    MotionEvent.ACTION_UP //松开
                    -> {
                        Log.d(TAG, "结束位置: " + event.rawX + " " + event.rawY)
                        val endX = event.rawX
                        val endY = event.rawY
                        //三角形边长1
                        val length1 = Math.abs(endX - mStartX)
                        //三角形边长2
                        val length2 = Math.abs(endY - mStartY)
                        //通过勾股定理计算间距
                        val distance = Math.sqrt(Math.pow(length1.toDouble(), 2.0) + Math.pow(length2.toDouble(), 2.0)).toInt()
                        Log.d(TAG, "距离: " + distance)
                        val temp = (distance * 1.44).toInt() //这里需要多尝试几次 找到最佳时间
                        exec("input swipe 360 800 360 800 " + temp + "\n")
                    }
                }
                return true
            }
        })

    }

    //显示logo悬浮图标
    private fun showLogoFloat(view: View) {
        FloatWindow
                .with(applicationContext)
                .setView(view)
                .setY(Screen.height, 0.1f)
                .setDesktopShow(true)
                .setTag("logo")
                .build()
        onResume()
    }

}
