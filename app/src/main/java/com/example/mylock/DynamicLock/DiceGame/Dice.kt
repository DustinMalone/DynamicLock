package com.example.mylock.DynamicLock.DiceGame

import android.content.res.Resources
import android.graphics.*
import android.util.Log
import com.example.mylock.DynamicLock.R
import java.util.*

/**
 * Created by LCB on 2018/12/11.
 */
public class Dice(res: Resources) {
    private lateinit var diceBitmap:Bitmap	//骰子的图片引用
    private lateinit var tempBitmap:Bitmap	//骰子的临时引用
    private var  diceWidth:Int = 0        //骰子的宽（6个面的骰子在一张图上画）
    var  diceHeight:Int=0		//骰子的高
    var  avgWidth:Int=0  	//骰子图片的平均宽度
//	int x, y;		//骰子显示的坐标
    private var nowNum:Int=0 ;		//骰子的当前值
    private lateinit var random: Random;		//获取随即对象
    lateinit var paint:Paint;		//画笔的引用

    init {
        paint = Paint();
        paint.isAntiAlias = true;	//消除画笔的锯齿
        initBitmap(res);		//初始化骰子图片
        diceWidth = diceBitmap.width;		//初始化图片宽度
        diceHeight = diceBitmap.height;		//初始化图片高度
        avgWidth = diceWidth / 6;		//初始化平均宽度
        nowNum = -1;
        random = Random();
        System.out.println("diceWidth=" + diceWidth +", diceHeight" + diceHeight + ", avgWidth" + avgWidth);
//		x = MySurfaceView.screen_width/2 - avgWidth/2;
//		y = MySurfaceView.screen_height/3 - diceHeight/2;
    }

    fun initBitmap(res:Resources){
        diceBitmap = BitmapFactory.decodeResource(res, R.drawable.dice);
    }

    fun  getbitmap():Bitmap	{
        //根据序号截取大图上相应骰子数图片
        return Bitmap.createBitmap(diceBitmap, avgWidth*(nowNum), 0, avgWidth, diceHeight);
    }

    fun playDice(canvas:Canvas,  x:Int,  y:Int){
        //随即绘制骰子
        canvas.drawColor(Color.WHITE);
        for (index in 1..3){
            when(index){
                1->{
                    nowNum = random.nextInt(6);
                    tempBitmap = getbitmap();
                    canvas.drawBitmap(tempBitmap,x-avgWidth-50.toFloat(), y.toFloat(), paint);
                }
                2->{
                    nowNum = random.nextInt(6);
                    tempBitmap = getbitmap();
                    canvas.drawBitmap(tempBitmap,x.toFloat(), y.toFloat(), paint);
                }
                3->{
                    nowNum = random.nextInt(6);
                    tempBitmap = getbitmap();
                    canvas.drawBitmap(tempBitmap,x+avgWidth+50.toFloat(), y.toFloat(), paint);
                }
            }
           Log.e("now",nowNum.toString())

        }

    }

    fun DrawDice( canvas:Canvas, x:Int,  y:Int){
        //绘制一个固定的骰子，即值为1的骰子，在投骰子的游戏开始前绘制
        tempBitmap = getbitmap();
        canvas.drawColor(Color.WHITE);
        System.out.println("----------------1-----------------");
        canvas.drawBitmap(tempBitmap,x-avgWidth-50.toFloat(), y.toFloat(), paint);
        canvas.drawBitmap(tempBitmap,x.toFloat(), y.toFloat(), paint);
        canvas.drawBitmap(tempBitmap,x+avgWidth+50.toFloat(), y.toFloat(), paint);
    }
}
