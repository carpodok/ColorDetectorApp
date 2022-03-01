package com.example.colordetectorapp.View

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.colordetectorapp.R
import com.example.colordetectorapp.adapter.SavedPhotosAdapter
import com.example.colordetectorapp.viewmodel.SavedPhotosViewModel
import java.io.File

class SavedPhotoActivity : AppCompatActivity() {


    private lateinit var savedPhotos_rv: RecyclerView
    lateinit var savedPhotosViewModel: SavedPhotosViewModel
    lateinit var files: Array<File>

    private lateinit var notFoundText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_photo)

        savedPhotosViewModel =ViewModelProviders.of(this).get(SavedPhotosViewModel::class.java)

        savedPhotos_rv = findViewById(R.id.saved_photos_rv)
        savedPhotos_rv.layoutManager = GridLayoutManager(this, 3)

        notFoundText = findViewById(R.id.noPhotoTextView)

        savedPhotosViewModel = SavedPhotosViewModel()

        files = savedPhotosViewModel.getphotos(this)

        val adapter = SavedPhotosAdapter(files.reversedArray(), this)

        savedPhotos_rv.adapter = adapter

        savedPhotosViewModel.getphotos(this)
        oberserData()

    }

    private fun oberserData() {

        savedPhotosViewModel.spList.observe(this, Observer {

            if (it.isNotEmpty()){
                notFoundText.visibility = View.GONE
                savedPhotos_rv.visibility = View.VISIBLE
            }
        })

        savedPhotosViewModel.spError.observe(this, Observer {

            if (it){
                notFoundText.visibility = View.GONE
                savedPhotos_rv.visibility = View.GONE
            }
        })

        savedPhotosViewModel.spNotFound.observe(this, Observer {

            if (it){
                notFoundText.visibility = View.VISIBLE
                savedPhotos_rv.visibility = View.GONE
            }
        })
    }
}