package com.innovaocean.adoptmeapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.innovaocean.adoptmeapp.databinding.ItemBreedBinding
import com.innovaocean.adoptmeapp.domain.Breed

class BreedAdapter(private val onBreedClicked: (Breed) -> Unit) :
    RecyclerView.Adapter<BreedAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Breed>() {
        override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBreedBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val breed = differ.currentList[position]
        val context = holder.itemView.context
        holder.bind(context, breed, onBreedClicked)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding:ItemBreedBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, breed: Breed, onBreedClicked: (Breed) -> Unit) {
            breed.image?.let {
                Glide.with(context).load(it.url).into(binding.breedImage)
            }
            binding.breedName.text = breed.name
            binding.root.setOnClickListener {
                onBreedClicked(breed)
            }
        }
    }
}