package com.example.colordetectorapp.handler

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.camera.view.PreviewView
import com.example.colordetectorapp.Model.ColorModel
import com.example.colordetectorapp.Util.ColorUtils
import com.example.colordetectorapp.viewmodel.ColorDetectViewModel

class ColorDetectHandler {

    private var pixel: Int = 0

    private var x = 0f
    private var y = 0f

    private var r = 0
    private var g = 0
    private var b = 0

    private var rgb = 0
    private var hex: String = ""

    private var name: String = ""

    private var bitmap: Bitmap? = null

    lateinit var currColor: ColorModel

    var detectViewModel: ColorDetectViewModel = ColorDetectViewModel()

    fun detect(cameraPreview: PreviewView, pointer: View): ColorModel {
        bitmap = cameraPreview.bitmap!!

        x = pointer.x + (pointer.width / 2).toFloat()
        y = pointer.y + (pointer.height / 2).toFloat()

        if (x >= cameraPreview.right){
            x  = cameraPreview.right - 1f
        }

        Log.d("cameraPreview bottom",cameraPreview.bottom.toString())
        Log.d("cameraPreview y",y.toString())
        Log.d("cameraPreview y",y.toString())


        pixel = bitmap!!.getPixel(x.toInt(), y.toInt())

        r = Color.red(pixel)
        g = Color.green(pixel)
        b = Color.blue(pixel)

        // colorText.text = "RGB: $r, $g, $b"

        detectViewModel.initData()
        name = detectViewModel.getColorNameFromRgb(r, g, b)!!

        rgb = Color.rgb(r, g, b)
        hex = Integer.toHexString(rgb and 0x00ffffff)

        currColor = ColorModel(name, r, g, b, hex)

        return currColor

    }

    fun detect(photo: ImageView, pointer: View): ColorModel {

        photo.isDrawingCacheEnabled = true
        bitmap = photo.drawingCache

        x = pointer.x + (pointer.width / 2).toFloat()
        y = pointer.y + (pointer.height / 2).toFloat()

        if (x >= photo.right){
            x  = photo.right - 1f
        }



        if (y >= photo.bottom){
            y = photo.bottom - 1f
        }

        pixel = bitmap!!.getPixel(x.toInt(), y.toInt())

        r = Color.red(pixel)
        g = Color.green(pixel)
        b = Color.blue(pixel)

        detectViewModel.initData()
        name = detectViewModel.getColorNameFromRgb(r, g, b)!!

        rgb = Color.rgb(r, g, b)
        hex = Integer.toHexString(rgb and 0x00ffffff)

        currColor = ColorModel(name, r, g, b, hex)

        return currColor

    }
}