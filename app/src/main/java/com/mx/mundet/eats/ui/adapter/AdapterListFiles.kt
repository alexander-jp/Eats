package com.mx.mundet.eats.ui.adapter

import android.graphics.ColorSpace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.ArraySet
import androidx.collection.arraySetOf
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mx.mundet.eats.R
import com.mx.mundet.eats.databinding.ItemsFilesChooserBinding
import com.mx.mundet.eats.ui.interfaces.OnItemClickListener
import com.mx.mundet.eats.ui.mvp.fileChooser.DirectoryModel
import java.io.File

/**
 * Created by Alexander Juárez with Date 08/04/2021
 * @author Alexander Juárez
 */

class AdapterListFiles : RecyclerView.Adapter<AdapterListFiles.VH>() {

    var items : ArraySet<DirectoryModel> = arraySetOf()
    var onClick : OnItemClickListener?=null
    var optionList : String?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.items_files_chooser, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val f = items.valueAt(position)
        optionList?.let {
            when(it){
                "folder" -> holder.title.text = f?.dirName
                else -> holder.title.visibility = View.GONE
            }
        }
        holder.tv_count.text  = "${f?.count ?:""}"
        when(f?.dirType){
            0-> holder.image.visibility = View.INVISIBLE //TODO DIRECTORY
            1-> holder.image.visibility = View.VISIBLE //TODO FILE OR PHOTOS
        }
        f?.icon?.let {
            holder.image.load(File(it)){
                placeholder(R.drawable.ic_round_photo_24)
            }
            holder.title.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.scrim_color_bg)
        }
        holder.itemView.setOnClickListener {
            onClick?.OnItemClickListener(it, position)
            holder.containerCard.isChecked = checkNotNull(f?.isSelected?.not())
        }
        Log.e("TAG", "onBindViewHolder: date created: ${f?.dateCreated}")
    }

    override fun getItemCount(): Int = items.size

    class VH(v: View) : RecyclerView.ViewHolder(v){
        private val _binding : ItemsFilesChooserBinding by lazy { ItemsFilesChooserBinding.bind(v) }
        val title = _binding.tvNameFile
        val image = _binding.imgvIconFiles
        val tv_count = _binding.tvCountFiles
        val containerCard = _binding.root
    }
}