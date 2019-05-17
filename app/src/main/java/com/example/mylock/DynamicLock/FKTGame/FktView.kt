package com.example.mylock.DynamicLock.FKTGame

import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.scrollView

/**
 * 自定义Component组件
 */

class FktView : AnkoComponent<FMainActivity> {
    override fun createView(ui: AnkoContext<FMainActivity>) = ui.apply {
       scrollView {

       }


    }.view

}