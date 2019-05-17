package com.example.mylock.DynamicLock.FKTGame

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import org.jetbrains.anko.*

/**
 * Created by LCB on 2018/9/18.
 */

class FMainActivity : AppCompatActivity(), AnkoLogger {

    lateinit var ballview:BallView

//    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//         setContentView(R.layout.activity_fmain)
        ballview=BallView(this)
        setContentView(ballview)
//        FktView().setContentView(this)


    }


    /**
     * Activity需要继承AnkoLogger
     */
    private fun debugLog() {


        info("hahah")
        debug("hahah")
        error("hahha")
        warn("hahha")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        ballview.onKeyDown(keyCode,event)
        return super.onKeyDown(keyCode, event);
    }



}