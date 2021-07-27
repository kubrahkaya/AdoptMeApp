package com.innovaocean.adoptmeapp.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.innovaocean.adoptmeapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val breed = args.breed
        view.apply {
            findViewById<TextView>(R.id.name).text = breed.name
            findViewById<TextView>(R.id.temperament).text = breed.temperament
            findViewById<TextView>(R.id.energyLevel).text = breed.energyLevel.toString()
            findViewById<TextView>(R.id.wikiUrl).text = breed.wikipediaUrl
            breed.image?.let {
                Glide.with(this).load(it.url).into(findViewById(R.id.image))
            }
        }
    }

}