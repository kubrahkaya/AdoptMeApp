package com.innovaocean.adoptmeapp

import com.innovaocean.adoptmeapp.data.BreedResponse
import com.innovaocean.adoptmeapp.data.ImageResponse
import com.innovaocean.adoptmeapp.domain.Breed
import com.innovaocean.adoptmeapp.domain.Image

object TestDataProvider {

    fun getBreedsResponse(): List<BreedResponse> = listOf(
        BreedResponse(
            id = "1",
            name = "Siamese",
            image = ImageResponse(""),
            temperament = "Good cat",
            wikipediaUrl = "www",
            energyLevel = 5
        )
    )

    fun getBreeds(): List<Breed> = listOf(
        Breed(
            id = "1",
            name = "Siamese",
            image = Image(""),
            temperament = "Good cat",
            wikipediaUrl = "www",
            energyLevel = 5
        )
    )
}