package com.example.mylock.DynamicLock.Activity;

import android.os.Bundle;
import android.os.Message;
import android.view.TextureView;
import android.view.View;

import com.example.mylock.DynamicLock.R;
import com.example.mylock.DynamicLock.base.BaseActivity;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.callback.IZegoLivePublisherCallback;
import com.zego.zegoliveroom.callback.IZegoLoginCompletionCallback;
import com.zego.zegoliveroom.constants.ZegoVideoViewMode;
import com.zego.zegoliveroom.entity.AuxData;
import com.zego.zegoliveroom.entity.ZegoStreamInfo;
import com.zego.zegoliveroom.entity.ZegoStreamQuality;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;

@ContentView(R.layout.activity_play)
public class PlayActivity  extends BaseActivity implements IZegoLoginCompletionCallback{

private ZegoLiveRoom zegoLiveRoom;

    @ViewInject(R.id.tv_push_video)
    private TextureView tv_push_video;



    @Override
    protected void init(Bundle savedInstanceState) {
        zegoLiveRoom=new ZegoLiveRoom();
        zegoLiveRoom.setZegoLivePublisherCallback(new ZegoCallback());

       boolean result= zegoLiveRoom.loginRoom("test","test",1,this);
        if (result){
            zegoLiveRoom.setPreviewView(tv_push_video);
            zegoLiveRoom.setPreviewViewMode(ZegoVideoViewMode.ScaleAspectFill);
            boolean startState= zegoLiveRoom.startPreview();
            if (!startState){
                SToast("startFailure");
            }
        }else{
            SToast("asdasdsa");
        }
    }

    @Override
    protected void ObjectMessage(Message msg) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLoginCompletion(int i, ZegoStreamInfo[] zegoStreamInfos) {

    }


    private class ZegoCallback implements IZegoLivePublisherCallback{

        @Override
        public void onPublishStateUpdate(int i, String s, HashMap<String, Object> hashMap) {

        }

        @Override
        public void onJoinLiveRequest(int i, String s, String s1, String s2) {

        }

        @Override
        public void onPublishQualityUpdate(String s, ZegoStreamQuality zegoStreamQuality) {

        }

        @Override
        public AuxData onAuxCallback(int i) {
            return null;
        }

        @Override
        public void onCaptureVideoSizeChangedTo(int i, int i1) {

        }

        @Override
        public void onMixStreamConfigUpdate(int i, String s, HashMap<String, Object> hashMap) {

        }
    }

}
