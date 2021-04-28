package com.example.filemanager

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class FileAdapter(private var context: Context, private var file: List<File>,private var click:OnFileClickListener) :
    RecyclerView.Adapter<FileView>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileView {
        return FileView(LayoutInflater.from(context).inflate(R.layout.file_container,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FileView, position: Int) {
        holder.name.text = file[position].name
        holder.name.isSelected
        var items = 0
        if(file[position].isDirectory)
        {
            val files = file[position].listFiles()
            if(files!=null)
            for(f in files)
            {
                if(!f.isHidden)
                {
                    items += 1
                }

            }
            holder.size.text = "$items Files"
        }
        else
        {
            holder.size.text = Formatter.formatShortFileSize(context, file[position].length())
        }

        holder.container.setOnClickListener {
            click.onFileClick(file[position])

        }
    }

    override fun getItemCount(): Int {
        return file.size
    }
}