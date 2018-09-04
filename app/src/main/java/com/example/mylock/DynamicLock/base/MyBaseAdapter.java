package com.example.mylock.DynamicLock.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.mylock.DynamicLock.R;

import org.xutils.image.ImageOptions;

import java.util.ArrayList;



/**
 * Created by Administrator on 2016/8/16.
 */
public abstract class MyBaseAdapter<t> extends BaseAdapter {
    protected ImageOptions options = new ImageOptions.Builder()
            // 是否忽略GIF格式的图片
            .setIgnoreGif(false)
            // 图片缩放模式
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP).
            // 下载中显示的图片
                    setLoadingDrawableId(R.mipmap.load)
            // 下载失败显示的图片
            .setFailureDrawableId(R.mipmap.unload)
            // 得到ImageOptions对象
            .build();
    protected Context mContext;
    protected LayoutInflater inflater;
    protected ArrayList<t> list = null;


    public MyBaseAdapter(Context c) {
        mContext = c;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (list == null) return 0;
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public ArrayList<t> getList() {
        return list;
    }

    /**
     * 重新设置数据，或者第一次设置数据
     *
     * @param list
     */
    public void setList(ArrayList<t> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 往list后面添加数据，叠加到后面
     *
     * @param list
     */
    public void addList(ArrayList<t> list) {
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        for (t obj : list) {
            this.list.add(obj);
        }
    }
}
