package com.example.colordetectorapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.colordetectorapp.R
import com.example.colordetectorapp.View.FullPhotoActivity
import java.io.File

class SavedPhotosAdapter(private val list: Array<File>, private val context : Context) :
    RecyclerView.Adapter<SavedPhotosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_photo_layout,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        holder.bind(item,context)

        holder.savedImg.setOnClickListener {
            val intent = Intent(context,FullPhotoActivity::class.java)
            intent.putExtra("photoIndex",list.size-position-1)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

         var savedImg : ImageView= view.findViewById(R.id.saved_photo_imageview)

        fun bind(file: File, context : Context) {
            Glide.with(context).load(file).into(savedImg)
        }


    }
}