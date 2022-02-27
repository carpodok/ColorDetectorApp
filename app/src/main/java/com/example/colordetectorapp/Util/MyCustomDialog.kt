package com.example.colordetectorapp.Util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.example.colordetectorapp.databinding.CustomDialogBinding

 abstract class MyCustomDialog(context: Context) : Dialog(context) {

    private lateinit var bindind: CustomDialogBinding

    lateinit var noBtn: Button
    lateinit var yesBtn: Button
    lateinit var title: TextView
    lateinit var body: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        bindind = CustomDialogBinding.inflate(layoutInflater)
        setContentView(bindind.root)

        setInit()

        yesBtn.setOnClickListener {
            clickYesBtn()
        }

        noBtn.setOnClickListener {
            clickNoBtn()
        }

    }



    abstract fun clickYesBtn()

    abstract fun clickNoBtn()





    private fun setInit() {

        noBtn = bindind.noBtn
        yesBtn = bindind.yesBtn
        title = bindind.dTitle
        body = bindind.dBody

    }


}