package com.mx.mundet.eats.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.mx.mundet.eats.R
import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.databinding.ItemListPersonBinding
import com.mx.mundet.eats.ui.interfaces.OnItemClickListener

/**
 * Created by Alexander Juárez with Date 18/03/2021
 * @author Alexander Juárez
 */

class AdapterListPerson : RecyclerView.Adapter<AdapterListPerson.VH>() {

    var lista: ArrayList<PersonasEntity> = arrayListOf()
    var onClick : OnItemClickListener?=null
    var longClick : OnItemClickListener?=null

    fun insertItem(p: PersonasEntity) {
        lista.add(p)
        notifyItemInserted(lista.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_list_person, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = lista[position]
        holder.nombre.text = p.nombre
        holder.descripcion.text = p.sexo
        holder.imagen.load(R.drawable.cuenta){
            transformations(CircleCropTransformation())
        }
        holder.itemView.setOnClickListener {
            onClick?.OnItemClickListener(it, position)
        }
        holder.itemView.setOnLongClickListener {
            longClick?.OnItemClickListener(it, position)
            holder.card.isChecked = true
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        private val _binding: ItemListPersonBinding by lazy { ItemListPersonBinding.bind(v) }
        val nombre = _binding.tvTitleMoviePopular
        val descripcion = _binding.tvDescriptionMoviePopular
        val imagen = _binding.imgvPictureMoviePopular
        val card = _binding.containerCardItem

    }
}