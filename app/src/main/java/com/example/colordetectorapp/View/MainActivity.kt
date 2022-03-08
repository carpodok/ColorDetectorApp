package com.example.colordetectorapp.View

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.colordetectorapp.databinding.ActivityMainBinding
import com.example.colordetectorapp.handler.ColorDetectHandler
import com.example.colordetectorapp.viewmodel.ColorDetectViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {

    private val detectHandler = ColorDetectHandler()

    private lateinit var binding: ActivityMainBinding

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraSelector: CameraSelector

    private var imageCapture: ImageCapture? = null
    private lateinit var imgCaptureExecutor: ExecutorService

    //Intent
    private var imgIndex by Delegates.notNull<Int>()

    // Views
    private lateinit var cameraPreview: PreviewView
    private lateinit var galleryBtn: Button
    private lateinit var takePhotoBtn: Button
    private lateinit var switchCameraBtn: Button
    lateinit var colorName: TextView
    lateinit var fabSavedfPhotos: FloatingActionButton
    lateinit var pointer: View
    lateinit var cardColor: CardView
    lateinit var card_color_preview: CardView
    lateinit var card_colorName: TextView
    lateinit var colorHex: TextView

    // ViewModel
    private lateinit var detectViewModel: ColorDetectViewModel

    private val cameraProviderResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            if (permissionGranted) {

                startCamera()

            } else {
                Snackbar.make(
                    binding.root,
                    "The camera permission is required",
                    Snackbar.LENGTH_INDEFINITE
                ).show()
            }
        }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setInit()

        startCamera()


        colorHex.setOnClickListener {
            copyText(colorHex.text.toString())
        }

        colorName.setOnClickListener {
            copyText(colorName.text.toString())
        }


        cameraPreview.setOnTouchListener { view, motionEvent ->

            // val rectCameraPreview = Rect(10,100,30,0)


            card_color_preview.y = motionEvent.y
            card_color_preview.x = motionEvent.x

            var pointerX = card_color_preview.x + card_color_preview.x / 2
            var pointerY = card_color_preview.y + 100

            val margin = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                15f,
                resources.displayMetrics
            )

            Log.d("y bottom",cameraPreview.bottom.toString())
            Log.d("y ",pointerY.toString())


            if (pointerY <= cameraPreview.top){
                pointerY = cameraPreview.top.toFloat()
            }else if(pointerY >= cameraPreview.bottom - margin){
                pointerY = cameraPreview.bottom- margin
            }

            pointer.y = pointerY

            if (pointerX >= cameraPreview.right - 50f) {
                pointerX = cameraPreview.right - 50f
            }

            pointer.x = pointerX

            if (motionEvent.action == MotionEvent.ACTION_MOVE || motionEvent.action == MotionEvent.ACTION_DOWN) {
                detect()
            }

            true
        }

        galleryBtn.setOnClickListener {
            clickGallery()
        }

        takePhotoBtn.setOnClickListener {
            takePhoto()

            animateFlash()
        }

        switchCameraBtn.setOnClickListener {
            switchCamera()
        }

        fabSavedfPhotos.setOnClickListener {
            clickSavedPhotos()
        }


    }

    @SuppressLint("SetTextI18n")
    private fun detect() {

            val currColor = detectHandler.detect(cameraPreview, pointer)

            val name = currColor.name
            val hex = currColor.hex
            val r = currColor.r
            val g = currColor.g
            val b = currColor.b

            colorName.text = name
            card_colorName.text = name
            colorHex.text = "#$hex"
            cardColor.setCardBackgroundColor(Color.rgb(r!!, g!!, b!!))


    }

    private fun startCamera() {
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
        }

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Log.d(TAG, "Use case binding failed")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        imageCapture?.let {
            val fileName = "JPEG_${System.currentTimeMillis()}"
            val file = File(externalMediaDirs[0], fileName)

            val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()
            it.takePicture(
                outputFileOptions,
                imgCaptureExecutor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                        Log.i(TAG, "The image has been saved in ${file.absolutePath}")

                        //Toast.makeText(this@MainActivity,"The image has been saved in ${file.toUri()}",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Toast.makeText(
                            binding.root.context,
                            "Error taking photo",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d(TAG, "Error taking photo:$exception")
                    }

                })
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun animateFlash() {
        binding.root.postDelayed({
            binding.root.foreground = ColorDrawable(Color.WHITE)
            binding.root.postDelayed({
                binding.root.foreground = null
            }, 50)
        }, 100)
    }

    private fun clickGallery() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, PERMISSION_CODE)
        } else {
            chooseImageGallery();
        }
    }

    private fun clickSavedPhotos() {

        startActivity(Intent(this, SavedPhotoActivity::class.java))
    }

    private fun switchCamera() {

        cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
            CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            CameraSelector.DEFAULT_BACK_CAMERA
        }

        startCamera()
    }

    private fun chooseImageGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, IMAGE_CHOOSE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CHOOSE) {
            try {
                val imageUri: Uri? = data?.data

                val intent = Intent(this, FullPhotoActivity::class.java)
                intent.putExtra("uri", imageUri.toString())
                startActivity(intent)


            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseImageGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun copyText(text: String) {
        val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("copy_text", text)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(applicationContext, "Copied $text", Toast.LENGTH_SHORT).show()
    }

    private fun setInit() {

        cameraProviderResult.launch(Manifest.permission.CAMERA)
        imgCaptureExecutor = Executors.newSingleThreadExecutor()

        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        // Views
        colorName = binding.colorText
        cameraPreview = binding.cameraPreview
        galleryBtn = binding.galleryBtn
        takePhotoBtn = binding.takePhotoBtn
        switchCameraBtn = binding.switchCameraBtn
        fabSavedfPhotos = binding.fabSavedPhotos
        pointer = binding.pointer
        colorHex = binding.colorHex

        cardColor = binding.cardColor
        card_colorName = binding.cardColorName
        card_color_preview = binding.cardColorPreview

        //ViewModel
        detectViewModel = ColorDetectViewModel()


        imgIndex = intent.getIntExtra("imgIndex", -1)
        if (imgIndex != -1) {
            cameraPreview.visibility = View.GONE
        }
    }

    private fun getPhotoList(): Array<File> {

        val directory = File(externalMediaDirs[0].absolutePath)
        val files = directory.listFiles() as Array<File>

        return files
    }

    companion object {
        const val TAG = "MainActivity"
        private const val IMAGE_CHOOSE = 1000
        private const val PERMISSION_CODE = 1001
    }
}