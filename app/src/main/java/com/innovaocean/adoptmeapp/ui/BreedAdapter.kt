package com.innovaocean.adoptmeapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.innovaocean.adoptmeapp.R
import com.innovaocean.adoptmeapp.data.BreedResponse

class BreedAdapter(private val onBreedClicked: (BreedResponse) -> Unit) :
    RecyclerView.Adapter<BreedAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<BreedResponse>() {
        override fun areItemsTheSame(oldItem: BreedResponse, newItem: BreedResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BreedResponse, newItem: BreedResponse): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_breed,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val breed = differ.currentList[position]

        val imageView = holder.itemView.findViewById<ImageView>(R.id.breedImage)
        val textView = holder.itemView.findViewById<TextView>(R.id.breedName)
        holder.itemView.apply {
            breed.image?.let {
                Glide.with(this).load(it.url).into(imageView)
            }
            textView.text = breed.name
            setOnClickListener {
                onBreedClicked(breed)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}