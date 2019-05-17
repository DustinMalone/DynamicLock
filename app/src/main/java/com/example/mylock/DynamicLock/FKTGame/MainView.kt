package com.example.mylock.DynamicLock.FKTGame

import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

/**
 * Created by LCB on 2018/9/18.
 */
/**
 * 自定义Component组件
 */

class MainView : AnkoComponent<FMainActivity> {
    override fun createView(ui: AnkoContext<FMainActivity>) = ui.apply {
//       scrollView {
//            padding = dip(30)
//            verticalLayout {
//
//                textView {
//                    text = "Anko Commons组件的使用"
//                    textColor = 0xff0000
//                }
//
//                val name = editText {
//                    hint = "请输入你名字"
//                }
//                button {
//                    text = "点击"
//                    setOnClickListener {  toast("${name.text}" + R.string.app_name) }
//                }.lparams(width = wrapContent, height = wrapContent) {
//                    topMargin = dip(10)
//                }
//                button {
//                    id = R.id.main_text
//                    text = "startActivity1"
//                    onClick {
//                        var intent = Intent(ctx, SecondActivity::class.java)
//                        intent.putExtra("id", "${name.text}")
//                        ctx.startActivity(intent)
//                    }
//                }
//                button {
//                    text = "startActivity2"
//                    onClick {
//                        ctx.startActivity<SecondActivity>("id" to "${name.text}")
////                        snackbar(this@button, "aa")
//                    }
//                }
//                button {
//                    text = "startActivity3"
//                    onClick {
//                        ctx.startActivity(intentFor<SecondActivity>("id" to "${name.text}").singleTop())
//                    }
//                }
//
//                seekBar {
//                    onSeekBarChangeListener {
//                        onProgressChanged { seekBar, progress, b -> toast("" + b + progress) }
//                    }
//
//                }
//                //include其他布局
//                include<View>(R.layout.activity_second) {
//
//                }
//
//                button {
//                    text = "dialog"
//                    onClick {
//
//                        //                        alert {
//                        // 一、dialog
////                            message = "hhahahah"
////                            title = "title"
////                            yesButton {
////                                toast("yes")
////                            }
////                            noButton {
////                                toast("no")
////                            }
////                        }.show()
//                        //二、dialog  默认样式dialog
////                        alert(Appcompat, "Some text message").show()
//                        //三、自定义dialog
////                        alert {
////                            customView {
////                                verticalLayout {
////                                    textView {
////                                        text = "title"
////                                    }
////                                }
////                            }
////                        }.show()
//                        //四、progressDialog
////                        progressDialog(message = "Please wait a bit…", title = "Fetching data").show()
//                        //五、单选对话框
//                        val countries = listOf("Russia", "USA", "Japan", "Australia")
//                        selector("Where are you from?", countries, { dialogInterface, i ->
//                            toast("So you're living in ${countries[i]}, right?")
//                        })
//                    }
//                }
//
//                button {
//                    text = "AnkoLogger"
//                    onClick {
//                    }
//                }
//            }
//        }.applyRecursively { view ->
//            //批量修改布局
//            when (view) {
//                is Button -> {
//                    view.textSize = 15f
//                    view.textColor = ctx.getColor(R.color.colorAccent)
//                }
//                is EditText -> {
//                    view.hint = "批量修改"
//                }
//            }
//        }
//
    }.view

}