package com.example.mylock.DynamicLock.GalleryView

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.mylock.DynamicLock.R
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton
import kotlinx.android.synthetic.main.activity_gallery.*





class GalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        init()
    }

    fun init(){
        var linearLayoutManager =  LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_info.layoutManager = linearLayoutManager;
        rv_info.adapter=GalleryAdapter(this)
        rv_info.setCanAlpha(true);
        rv_info.setCanScale(true);
        rv_info.setBaseScale(0.5f);
        rv_info.setBaseAlpha(0.95f);



    var ImageView =ImageView(this)
        ImageView.setImageResource(R.mipmap.ic_launcher)
        var l=FloatingActionButton.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        l.width=120
        l.height=120
        l.setMargins(0,0,0,0)
        val actionButton = FloatingActionButton.Builder(this)
                .setContentView(ImageView, l)
                .setPosition(FloatingActionButton.POSITION_RIGHT_CENTER)
                .build()
        actionButton.isLongClickable=true




        var itemBuilder =  SubActionButton.Builder(this);
// repeat many times:
        var itemIcon =  ImageView(this);
        itemIcon.setImageResource(R.mipmap.ic_launcher);
        var button1 = itemBuilder.setContentView(itemIcon).build();

        val circleMenu = FloatingActionMenu.Builder(this)
                .setStartAngle(90) // A whole circle!
                .setEndAngle(270)
                .setRadius(100)
                .addSubActionView(button1)
                .attachTo(actionButton)
                .build()

        actionButton.setOnLongClickListener {
            Log.e("qwe","3")
            if (!circleMenu.isOpen){
                circleMenu.open(true)
                circleMenu.updateItemPositions()
            }

            true
        }


        actionButton.setOnClickListener {
            Log.e("qwe","2")
        }
    }
}
