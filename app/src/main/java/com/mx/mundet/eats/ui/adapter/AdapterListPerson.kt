package com.mx.mundet.eats.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mx.mundet.eats.R
import com.mx.mundet.eats.bd.Entity.PersonasEntity
import com.mx.mundet.eats.databinding.ItemListPersonBinding

/**
 * Created by Alexander Juárez with Date 18/03/2021
 * @author Alexander Juárez
 */

class AdapterListPerson : RecyclerView.Adapter<AdapterListPerson.VH>() {

    var lista: ArrayList<PersonasEntity> = arrayListOf()

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
        //holder.imagen.setImageResource(R.drawable.ic_launcher_background)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        private val _binding: ItemListPersonBinding by lazy { ItemListPersonBinding.bind(v) }
        val nombre = _binding.tvTitleMoviePopular
        val descripcion = _binding.tvDescriptionMoviePopular
        val imagen = _binding.imgvPictureMoviePopular
    }
}