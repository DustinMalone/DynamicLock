package com.example.mylock.DynamicLock.Activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import com.example.mylock.DynamicLock.R
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.activity_show_image.*
import java.util.*

class ShowImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image)

        val banList = ArrayList<Int>()
        banList.add(R.drawable.simga)
        banList.add(R.drawable.simgb)
        //banner设置
        mbanner.setDelayTime(10000)//设置轮播时间
        mbanner.setImageLoader(object :ImageLoader(){
            override fun displayImage(context: Context?, path: Any, imageView: ImageView?) {
                Log.e("asd",path.toString())
                imageView!!.setImageResource(path.toString().toInt())
            }

        })
        mbanner.setImages(banList).start()
    }



}
