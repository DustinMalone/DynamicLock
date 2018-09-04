package com.example.mylock.DynamicLock.Activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.mylock.DynamicLock.NumerDance.RiseNumberTextView
import com.example.mylock.DynamicLock.Prize.GamePrizeAdapter
import com.example.mylock.DynamicLock.Prize.GridSpaceItemDecoration
import com.example.mylock.DynamicLock.R
import com.example.mylock.DynamicLock.base.BaseActivity
import com.zhy.autolayout.utils.AutoUtils
import org.xutils.view.annotation.ContentView
import org.xutils.view.annotation.ViewInject







@ContentView(R.layout.activity_num_dance)
class NumDanceActivity : BaseActivity() {

    @ViewInject(R.id.rntv_num_dance)
    lateinit var rntv:RiseNumberTextView

//    @ViewInject(R.id.snow_view)
//    lateinit var snow_view: SnowView

//    @ViewInject(R.id.tv_ready)
//    lateinit var tv_ready: TextView

    @ViewInject(R.id.wrv_prize)
    lateinit var wrv_prize: RecyclerView


    @ViewInject(R.id.btn_start)
    lateinit var btn_start: Button

    @ViewInject(R.id.tv_game_prize_remark)
    lateinit var tv_game_prize_remark: TextView

    //礼品选择器
    var sel_handle=Handler()



    //上次选中的
    private var lastSelect=0
    //总奖品数量
    private var sumPrizeNum=40

   var game_prize_adapter:GamePrizeAdapter?=null;

    override fun onClick(v: View) {
        when(v!!.id){
            R.id.btn_start->{
                if(rntv.isRunning) {
                    rntv.stop()
                    btn_start.text="开始"
                    sel_handle.removeCallbacks(sel_Runnable)
                }else{
                    rntv.start()
                    btn_start.text="停止"
                    sel_handle.post(sel_Runnable)
                }
            }
        }
    }

    override fun init(savedInstanceState: Bundle?) {
//        snow_view.startSnowAnim(SnowUtils.SNOW_LEVEL_HEAVY)
        tv_game_prize_remark.text= Html.fromHtml("说明:<br/>" +
                "按到<font color='#FFE511'>10:00</font>选中的礼盒免费<br/>" +
                "婼柜里无娃娃，则换为5元话费卷")
        wrv_prize.layoutManager=GridLayoutManager(this,4)
        wrv_prize.addItemDecoration(GridSpaceItemDecoration(AutoUtils.getPercentHeightSize(20),AutoUtils.getPercentWidthSize(5),AutoUtils.getPercentHeightSize(20),AutoUtils.getPercentWidthSize(5)))
        game_prize_adapter=GamePrizeAdapter(this,ArrayList<String>(),false,sumPrizeNum)
        wrv_prize.adapter=game_prize_adapter
        rntv.withNumber(1100)
        rntv.setOnEnd {
            btn_start.text="开始"
//            game_prize_adapter!!.isShowSelect=false
//            game_prize_adapter!!.notifyDataSetChanged()
        }
//        rntv.setOnTimeTick {
//           game_prize_adapter!!.apply {
//               if (rntv.isRunning){
//                       if (this.getLastSelect()+1>=sumPrizeNum){
//                           this.setLastSelect(0)
//                       }else{
//                           this.setLastSelect(this.getLastSelect()+1)
//                       }
//                       this.isShowSelect=true
//                        this.notifyDataSetChanged()
//               }
//            }
//
//        }
    }

    override fun ObjectMessage(msg: Message?) {
    }


    //选择礼品
    var sel_Runnable= Runnable {
        if (rntv.isRunning){
            if (game_prize_adapter!!.getLastSelect()+1>=sumPrizeNum){
                game_prize_adapter!!.setLastSelect(0)
            }else{
                game_prize_adapter!!.setLastSelect(game_prize_adapter!!.getLastSelect()+1)
            }
            game_prize_adapter!!.isShowSelect=true
            game_prize_adapter!!.notifyDataSetChanged()
        }
        startSel()
    }

    private fun startSel(){
        sel_handle.postDelayed(sel_Runnable,20)
    }


}
