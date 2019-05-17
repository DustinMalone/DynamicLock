package com.example.mylock.DynamicLock.DiceGame

import android.app.Activity
import android.graphics.Canvas
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.Button
import com.example.mylock.DynamicLock.R

class DiceGameActivity : Activity(), View.OnClickListener,
        SensorEventListener {

    lateinit var  play:Button;
    lateinit var  reset:Button;

    lateinit var view:MySurfaceView;
    lateinit var dice:Dice;
    lateinit var canvas:Canvas;

    var sensorManager:SensorManager? = null;		//传感器管理器
    var vibrator:Vibrator? = null;		//振动传感器

   override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dice_game);

//        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager;		//取得传感器管理器的实例
//        vibrator = getSystemService(Service.VIBRATOR_SERVICE) as Vibrator;

        view = findViewById(R.id.view) as MySurfaceView
        dice =  Dice(resources)
        play = findViewById(R.id.play) as Button
        reset = findViewById(R.id.reset) as Button
        canvas = Canvas();

        play.setOnClickListener(this);
        reset.setOnClickListener(this);

    }


//    override fun  onCreateOptionsMenu( menu:Menu):Boolan {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu);
//        return true;
//    }



    override fun onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        System.out.println("-----10-----");
        //遥感器
//        sensorManager!!.unregisterListener(this);
    }


    override fun onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        System.out.println("-----11-----");
        //传感器
//        sensorManager!!.registerListener(this,
//                sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//                SensorManager.SENSOR_DELAY_NORMAL);
    }


    override fun onClick(v:View) {
        // TODO Auto-generated method stub
        if(v == play){
            view.i = 1;
            view.isRun = true;
        }else if(v == reset){
            canvas = view.holder.lockCanvas();
            dice.DrawDice(canvas, view.Dice_x, view.Dice_y);
            view.holder.unlockCanvasAndPost(canvas);
        }
    }

   override fun onAccuracyChanged( sensor:Sensor,  accuracy:Int) {
        // TODO Auto-generated method stub

    }


    override fun onSensorChanged( event:SensorEvent) {
        // TODO Auto-generated method stub
        System.out.println("-----12-----");
        var sensorType = event.sensor.type;		//得到传感器的类型
        //values的值，values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        System.out.println("-----13-----");
        var values = event.values;
        System.out.println("-----14-----");
        if(sensorType == Sensor.TYPE_ACCELEROMETER){  //如果传感器类型是加速度计
            System.out.println("-----15-----");
            if(Math.abs(values[0])> 10
                    || Math.abs(values[1]) > 10
                    || Math.abs(values[2] )> 10){
                System.out.println("-----16-----");
                vibrator!!.vibrate(300);
                view.isRun = true;
                System.out.println("-----摇一摇-----");
            }
        }
    }
}
