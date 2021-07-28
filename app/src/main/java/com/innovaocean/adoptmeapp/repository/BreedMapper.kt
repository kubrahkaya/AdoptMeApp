package com.innovaocean.adoptmeapp.repository

import com.innovaocean.adoptmeapp.data.BreedResponse
import com.innovaocean.adoptmeapp.domain.Breed

class BreedMapper {

    fun mapToDomain(data: List<BreedResponse>): List<Breed> {
        return data.map {
            Breed(it.id, it.name, it.image, it.temperament, it.wikipediaUrl, it.energyLevel)
        }
    }
}