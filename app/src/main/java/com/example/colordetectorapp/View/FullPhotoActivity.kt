package com.example.colordetectorapp.View

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.colordetectorapp.R
import com.example.colordetectorapp.Util.ColorUtils
import com.example.colordetectorapp.adapter.SavedPhotosAdapter
import com.example.colordetectorapp.databinding.ActivityPhotoFullBinding
import com.example.colordetectorapp.handler.ColorDetectHandler
import com.example.colordetectorapp.viewmodel.SavedPhotosViewModel
import java.io.File

class FullPhotoActivity : AppCompatActivity() {

    private val detectHandler = ColorDetectHandler()

    private lateinit var bindind: ActivityPhotoFullBinding

    private var index: Int = 0
    private lateinit var uri: Uri

    lateinit var fileName: String

    // Views
    private lateinit var photo: ImageView
    private lateinit var deleteBtn: Button
    private lateinit var fp_card_color_preview: CardView
    private lateinit var fp_cardColor: CardView
    private lateinit var fp_colorHex: TextView
    private lateinit var fp_pointer: View
    private lateinit var fp_colorName: TextView
    private lateinit var fp_card_colorName: TextView

    private val savedPhotosViewModel = SavedPhotosViewModel()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = ActivityPhotoFullBinding.inflate(layoutInflater)
        setContentView(bindind.root)

        setInit()

        fp_colorHex.setOnClickListener {
            copyText(fp_colorHex.text.toString())
        }

        fp_colorName.setOnClickListener {
            copyText(fp_colorName.text.toString())
        }

        deleteBtn.setOnClickListener {

            fileName = getPhotoList()[index].name

            showDialog()

        }

        photo.setOnTouchListener { view, motionEvent ->

           setCardinates(motionEvent)

            detect(motionEvent)

            true
        }
    }

    private fun setCardinates(motionEvent: MotionEvent){

        fp_card_color_preview.y = motionEvent.y + 50
        fp_card_color_preview.x = motionEvent.x

        fp_pointer.y = fp_card_color_preview.y + 100
        fp_pointer.x = fp_card_color_preview.x + fp_card_color_preview.x / 2

        if (fp_pointer.x >= photo.right - 50f){
            fp_pointer.x = photo.right - 50f
        }
    }

    private fun setInit() {

        // Views
        photo = bindind.specificPhotoImageView
        deleteBtn = bindind.deleteBtn
        fp_pointer = bindind.fpPointer
        fp_cardColor = bindind.fpCardColor
        fp_card_color_preview = bindind.fpCardColorPreview
        fp_colorHex = bindind.fpColorHex
        fp_colorName = bindind.fpColorName
        fp_card_colorName = bindind.fpCardColorName

        // Value
        index = intent.getIntExtra("photoIndex", 0)
        val uriString = intent.getStringExtra("uri")


        if (uriString != null){
            uri = Uri.parse(uriString)
            Glide.with(this).load(uri).into(photo)
            deleteBtn.visibility = View.INVISIBLE

        }else{
            Glide.with(this).load(getPhotoList()[index]).into(photo)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun detect(motionEvent: MotionEvent) {

        if (motionEvent.action == MotionEvent.ACTION_MOVE || motionEvent.action == MotionEvent.ACTION_DOWN) {

            val currColor = detectHandler.detect(photo, fp_pointer)

            val name = currColor.name
            val hex = currColor.hex
            val r = currColor.r
            val g = currColor.g
            val b = currColor.b

            fp_colorHex.text = "#$hex"
            fp_colorName.text = name
            fp_card_colorName.text = name

            fp_cardColor.setCardBackgroundColor(Color.rgb(r!!, g!!, b!!))

        }
    }

    private fun showDialog() {
        val alertDialog = AlertDialog.Builder(this)

        alertDialog.apply {
            setIcon(R.drawable.ic_delete)
            setTitle("Delete")
            setMessage("Are you sure to delete this photo ?")
            setPositiveButton("Yes") { dialog, _ ->
                delete(fileName)
                intentToSavedPhotos()
                Toast.makeText(context,"Successfully deleted",Toast.LENGTH_LONG).show()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()

            }

        }.create().show()
    }

    private fun copyText(text: String) {
        val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("copy_text", text)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(applicationContext, "Copied $text", Toast.LENGTH_SHORT).show()
    }

    private fun intentToSavedPhotos(){
        val intent = Intent(this, SavedPhotoActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    private fun delete(fileName: String) {

        val file = File(externalMediaDirs[0], fileName)

        if (file.exists()) {
            file.delete()
        }
    }

    private fun getPhotoList(): Array<File> {

        return savedPhotosViewModel.getphotos(this)

    }
}