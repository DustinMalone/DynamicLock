package com.example.mylock.DynamicLock.DiceGame

import android.content.Context
import android.graphics.Canvas
import android.os.Handler
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView



/**
 * Created by LCB on 2018/12/11.
 */
class MySurfaceView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback, Runnable {
    companion object {
        var  screen_width:Int=0;		//设备屏幕的宽
        var  screen_height:Int=0;		//设备屏幕的高
    }

    var Dice_x:Int=0;
    var Dice_y:Int=0;
    var th:Thread? = null;		//取得线程类的引用
    var flag:Boolean = false;		//线程执行的标志位
    var isRun:Boolean=false;		//控制骰子的投掷
     var canvas:Canvas? = null;
    var dice:Dice?=null;	//骰子的引用
    private  var sleepSpan:Int = 150;		//线程休眠时间30
    var i:Int=0;		//用于循环的
    var mHandler= Handler();


    init{
        dice  =  Dice(context.resources);
        holder!!.addCallback(this);		//取得surfaceView的监视器
        th =  Thread(this);		//实例化线程
    }



   override fun surfaceChanged( holder:SurfaceHolder,  format:Int,  width:Int,
             height:Int) {
        // TODO Auto-generated method stub
    }

    override fun surfaceCreated(holder:SurfaceHolder) {
        // TODO Auto-generated method stub
        screen_width = this.width;		//取得屏幕宽度
        screen_height = this.height;	//取得屏幕高度
        Dice_x =screen_width/2 - dice!!.avgWidth/2;		//计算骰子的X位置
        Dice_y =screen_height/3 - dice!!.diceHeight/2;		//计算骰子的Y位置
//		Dice_x = 300;
//		Dice_y =200;
        System.out.println("screen_width=" + screen_width + ", screen_height=" + screen_height + ", Dice_x=" + Dice_x + ", Dice_y=" + Dice_y);
        flag = true;	//给flag设置初始值
        th!!.start();	//启动线程
    }

   override fun surfaceDestroyed(holder:SurfaceHolder) {
        // TODO Auto-generated method stub
    }

    override fun run(){
        while(flag){
            if(isRun){	//用判断语句控制是否进入骰子的投掷过程
                for(i in 1..10){
                    //随机取骰子值10次，完成投掷，建立此循环的目的是为了让线程持续执行，让投掷可控，
                    canvas = holder!!.lockCanvas();
                    //取得surfaceView的canvas
                    dice!!.playDice(canvas!!, Dice_x, Dice_y);//中
                    //随机投掷骰子
                    if(canvas != null){
                        holder.unlockCanvasAndPost(canvas);	//释放holder的画布
                    }
                }
                isRun = false;
            }
        }
        try{
            Thread.sleep(sleepSpan.toLong());
        }catch( e:Exception){
            e.printStackTrace();
        }
    }
}
