package com.example.colordetectorapp.View

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.colordetectorapp.Model.ColorModel
import com.example.colordetectorapp.Util.ColorUtils
import com.example.colordetectorapp.databinding.ActivityMainBinding
import com.example.colordetectorapp.handler.ColorDetectHandler
import com.example.colordetectorapp.viewmodel.ColorDetectViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {

    private val detectHandler = ColorDetectHandler()


    private lateinit var binding: ActivityMainBinding
    lateinit var bitmap: Bitmap

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraSelector: CameraSelector

    private var imageCapture: ImageCapture? = null
    private lateinit var imgCaptureExecutor: ExecutorService

    //Intent
    private var imgIndex by Delegates.notNull<Int>()

    // Views
    private lateinit var detectingImageView: ImageView
    private lateinit var cameraPreview: PreviewView
    private lateinit var galleryBtn: Button
    private lateinit var takePhotoBtn: Button
    private lateinit var switchCameraBtn: Button
    lateinit var colorTextView: TextView
    lateinit var fabSavedfPhotos: FloatingActionButton
    lateinit var pointer: View
    lateinit var cardColor: CardView
    lateinit var card_color_preview: CardView
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
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*val colorPalatte = binding.colorPalatte

        colorPalatte.isDrawingCacheEnabled = true
        colorPalatte.buildDrawingCache(true)

        var name : String


        colorPalatte.setOnTouchListener { view, motionEvent ->

            if (motionEvent.action == MotionEvent.ACTION_MOVE || motionEvent.action == MotionEvent.ACTION_DOWN){

                bitmap = colorPalatte.drawingCache

                val pixel = bitmap.getPixel(motionEvent.x.toInt(),motionEvent.y.toInt())

                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)


               // colorText.text = "RGB: $r, $g, $b"

                val colorUtils = ColorUtils()

                name = colorUtils.getColorNameFromRgb(r,g,b).toString()

                colorText.text = "Color Name : $name"


            }

            true
        }
*/

        setInit()

        startCamera()

        cameraPreview.setOnTouchListener { view, motionEvent ->

            // val rectCameraPreview = Rect(10,100,30,0)
            val touchableArea = Rect()
            cameraPreview.getHitRect(touchableArea)

            card_color_preview.y = motionEvent.y
            card_color_preview.x = motionEvent.x

            var cx = motionEvent.x

            var x = card_color_preview.x + card_color_preview.x / 2

            detect(motionEvent)

            /* Toast.makeText(this,(rectf.right).toString(),Toast.LENGTH_SHORT).show()
             Toast.makeText(this,(rectf.left).toString(),Toast.LENGTH_SHORT).show()*/

            /*Log.d("left         :",(rectf.left).toString())
            Log.d("right        :", (rectf.right).toString())
            Log.d("top          :", (rectf.top).toString())
            Log.d("bottom       :", (rectf.bottom).toString())*/

            //
            /*
             pointer.y = card_color_preview.y + 100
             pointer.x = x
 */



            val bitmapRect = Rect()


            pointer.y = card_color_preview.y + 100
            //  pointer.x = card_color_preview.x

            pointer.x = x

            /* pointer.setOnTouchListener { view, motionEvent ->
                 when (motionEvent.actionMasked) {
                     MotionEvent.ACTION_DOWN -> {
                         dX = motionEvent.x
                         dY = motionEvent.y
                     }
                     MotionEvent.ACTION_MOVE -> {
                         val movedX: Float = motionEvent.x
                         val movedY: Float = motionEvent.y

                         val distanceX: Float = movedX - dY
                         val distanceY: Float = movedY - dY

                         pointer.x = pointer.x + distanceX
                         pointer.y = pointer.y + distanceY
                     }
                 }
                 true
             }*/

            true
        }

        detectingImageView.setOnTouchListener { view, motionEvent ->
            // detect(motionEvent, false)
            true
        }

        galleryBtn.setOnClickListener {
            clickGallery()
        }

        takePhotoBtn.setOnClickListener {
            takePhoto()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                animateFlash()
            }
        }

        switchCameraBtn.setOnClickListener {
            switchCamera()
        }

        fabSavedfPhotos.setOnClickListener {
            clickSavedPhotos()
        }
    }

    private fun changTouchableArea(view: View, extraSpace: Int) {

        val parent = view.parent as View

        val touchableArea = Rect()
        //cameraPreview.getLocalVisibleRect(rectCameraPreview)
        cameraPreview.getHitRect(touchableArea)

        touchableArea.right -= extraSpace
        touchableArea.top -= extraSpace
        touchableArea.left -= extraSpace
        touchableArea.bottom -= extraSpace

        parent.touchDelegate = TouchDelegate(touchableArea, view)


    }

    @SuppressLint("SetTextI18n")
    private fun detect(motionEvent: MotionEvent) {

        if (motionEvent.action == MotionEvent.ACTION_MOVE || motionEvent.action == MotionEvent.ACTION_DOWN) {

            /*  bitmap = cameraPreview.bitmap!!

              val x = pointer.x + (pointer.width / 2).toFloat()
              val y = pointer.y + (pointer.height / 2).toFloat()

              val pixel = bitmap.getPixel(x.toInt(), y.toInt())
              // val pixel = bitmap.getPixel((pointer.left) + (pointer.measuredWidth / 2), (pointer.top) + (pointer.measuredHeight / 2))

              val r = Color.red(pixel)
              val g = Color.green(pixel)
              val b = Color.blue(pixel)

              // colorText.text = "RGB: $r, $g, $b"


              detectViewModel.initData()
              val name = detectViewModel.getColorNameFromRgb(r,g,b)

              val rgbInt = Color.rgb(r, g, b)
              val hex = Integer.toHexString(rgbInt and 0x00ffffff)*/

            val currColor = detectHandler.detect(cameraPreview, pointer)

            val name = currColor.name
            val hex = currColor.hex
            val r = currColor.r
            val g = currColor.g
            val b = currColor.b

            colorTextView.text = "Color Name : $name "
            colorHex.text = name
            cardColor.setCardBackgroundColor(Color.rgb(r!!, g!!, b!!))

        }
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

            // val pathName : String = Environment.DIRECTORY_DCIM + File.separator + fileName
            // val file = File(Environment.DIRECTORY_DCIM + File.separator, fileName)
            // val file = File(pathName)

            // val file = createImageFile()

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

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            } else {
                chooseImageGallery();
            }
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
        /*val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CHOOSE) */
         */

        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, IMAGE_CHOOSE)
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

    private fun setInit() {

        cameraProviderResult.launch(Manifest.permission.CAMERA)
        imgCaptureExecutor = Executors.newSingleThreadExecutor()

        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        // Views
        colorTextView = binding.colorText
        cameraPreview = binding.cameraPreview
        galleryBtn = binding.galleryBtn
        takePhotoBtn = binding.takePhotoBtn
        switchCameraBtn = binding.switchCameraBtn
        fabSavedfPhotos = binding.fabSavedPhotos
        pointer = binding.pointer
        detectingImageView = binding.detectingImageView
        cardColor = binding.cardColor
        colorHex = binding.colorHex
        card_color_preview = binding.cardColorPreview

        //ViewModel
        detectViewModel = ColorDetectViewModel()


        imgIndex = intent.getIntExtra("imgIndex", -1)
        if (imgIndex != -1) {
            cameraPreview.visibility = View.GONE
            detectingImageView.visibility = View.VISIBLE

            val file = getPhotoList()[imgIndex]
            Glide.with(this).load(file).into(detectingImageView)
        }
    }

    private fun getPhotoList(): Array<File> {

        val directory = File(externalMediaDirs[0].absolutePath)
        val files = directory.listFiles() as Array<File>

        return files
    }

    companion object {
        const val TAG = "MainActivity"
        private val IMAGE_CHOOSE = 1000
        private val PERMISSION_CODE = 1001
    }
}