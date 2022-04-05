package com.example.perros.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.perros.databinding.PerroFotoBinding
import com.squareup.picasso.Picasso

//une interfaz con código
class PerroViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val binding = PerroFotoBinding.bind(view)
    fun bind(imagenstring: String){
        Picasso.get().load(imagenstring).into(binding.fotoPerro)
    }

}