package com.innovaocean.adoptmeapp

import com.innovaocean.adoptmeapp.data.BreedResponse
import com.innovaocean.adoptmeapp.data.ImageResponse

object TestDataProvider {

    fun getBreeds(): List<BreedResponse> = listOf(
        BreedResponse(
            id = "1",
            name = "Siamese",
            image = ImageResponse(""),
            temperament = "Good cat",
            wikipediaUrl = "www",
            energyLevel = 5
        ),
        BreedResponse(
            id = "2",
            name = "Russian Blue",
            image = ImageResponse(""),
            temperament = "Good cat",
            wikipediaUrl = "www",
            energyLevel = 6
        ),
        BreedResponse(
            id = "3",
            name = "Cyprus",
            image = ImageResponse(""),
            temperament = "Bad cat",
            wikipediaUrl = "www",
            energyLevel = 7
        )
    )
}