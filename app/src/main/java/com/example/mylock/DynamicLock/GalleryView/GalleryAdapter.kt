package com.example.mylock.DynamicLock.GalleryView

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.mylock.DynamicLock.R
import com.zhy.autolayout.AutoRelativeLayout


/**
 * Created by LCB on 2018/8/17.
 *
 */
public class GalleryAdapter(var mContext: Context): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        var holder=ViewHolder(View.inflate(mContext, R.layout.item_gallery, AutoRelativeLayout(mContext)))

        return  holder
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

    }

    override fun getItemCount(): Int {
        return  10
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var iv_item_game_prize_img: ImageView =itemView.findViewById(R.id.iv_item_game_prize_img) as ImageView

    }


}