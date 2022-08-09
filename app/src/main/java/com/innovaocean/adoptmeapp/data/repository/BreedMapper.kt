package com.innovaocean.adoptmeapp.data.repository

import com.innovaocean.adoptmeapp.data.BreedResponse
import com.innovaocean.adoptmeapp.data.ImageResponse
import com.innovaocean.adoptmeapp.domain.Breed
import com.innovaocean.adoptmeapp.domain.Image

fun List<BreedResponse>.mapToDomain(): List<Breed> {
    return this.map {
        Breed(
            it.id,
            it.name,
            mapImagetoDomain(it.image),
            it.temperament,
            it.wikipediaUrl,
            it.energyLevel
        )
    }
}

private fun mapImagetoDomain(image: ImageResponse?): Image? {
    return image?.let { Image(image.url) }
}
