package com.innovaocean.adoptmeapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.innovaocean.adoptmeapp.R
import com.innovaocean.adoptmeapp.databinding.FragmentDetailBinding
import com.innovaocean.adoptmeapp.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args: DetailFragmentArgs by navArgs()
    private val binding by viewBinding(FragmentDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val breed = args.breed
        view.apply {
            binding.name.text = breed.name
            binding.temperament.text = breed.temperament
            binding.energyLevel.text = breed.energyLevel.toString()
            binding.wikiUrl.text = breed.wikipediaUrl
            breed.image?.let {
                Glide.with(this).load(it.url).into(findViewById(R.id.image))
            }
        }
    }

}