package com.example.filemanager

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class FileView(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val name :TextView= itemView.findViewById(R.id.file_name)
       val size:TextView =itemView.findViewById(R.id.file_size)
        val container : LinearLayout= itemView.findViewById(R.id.container)
        private val img:ImageView = itemView.findViewById(R.id.image_file)
    }

