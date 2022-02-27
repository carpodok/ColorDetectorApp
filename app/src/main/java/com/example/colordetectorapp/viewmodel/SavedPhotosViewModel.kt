package com.example.colordetectorapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.colordetectorapp.Model.ColorModel
import java.io.File

class SavedPhotosViewModel : ViewModel() {

    var spError = MutableLiveData<Boolean>()
    var spNotFound = MutableLiveData<Boolean>()
    lateinit var photos: Array<File>
    var spList: MutableLiveData<Array<File>> = MutableLiveData()

    fun getphotos(context: Context): Array<File> {

        val directory = File(context.externalMediaDirs[0].absolutePath)
        photos = directory.listFiles() as Array<File>
        spList.value = photos

        if (photos.isEmpty()) {
            spNotFound.value = true
            spError.value = false
        } else {
            spError.value = false
            spNotFound.value = false
        }

        return photos
    }

}