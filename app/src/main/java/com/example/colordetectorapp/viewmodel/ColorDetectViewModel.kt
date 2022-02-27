package com.example.colordetectorapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.colordetectorapp.Model.ColorModel
import com.example.colordetectorapp.Util.ColorUtils


class ColorDetectViewModel : ViewModel() {

    lateinit var colorNotFound : MutableLiveData<Boolean>
    lateinit var detectError : MutableLiveData<Boolean>
    lateinit var colorList : MutableLiveData<ArrayList<ColorModel>>
    lateinit var colorName : MutableLiveData<String>
    lateinit var colors : ArrayList<ColorModel>


     fun initData(){

         colorNotFound = MutableLiveData()
         detectError = MutableLiveData()
         colorList = MutableLiveData()

        colors = ColorUtils().getColorList()
        colorList.value = colors
    }

    fun getColorNameFromRgb(r: Int, g: Int, b: Int) : String?{
        val colorList: ArrayList<ColorModel> = colors
        var closestMatch: ColorModel? = null
        var minMSE = Int.MAX_VALUE
        var mse: Int
        for (c: ColorModel in colorList) {
            mse = c.computeMSE(r, g, b)
            if (mse < minMSE) {
                minMSE = mse
                closestMatch = c
            }
        }
        return if (closestMatch != null) {
            colorNotFound.value = false
            detectError.value = false
            closestMatch.name
        } else {
            colorNotFound.value = true
            ""
        }
    }

}