package com.innovaocean.adoptmeapp.data.repository

import com.innovaocean.adoptmeapp.domain.Breed

interface BreedRepository {

    suspend fun searchForBreeds(searchQuery: String): BreedResponse

    sealed class BreedResponse {
        data class Success(val breeds: List<Breed>) : BreedResponse()
        object EmptyList : BreedResponse()
        object ResponseError : BreedResponse()
        object ResponseUnsuccessful : BreedResponse()
        object Error : BreedResponse()
    }

}