package com.innovaocean.adoptmeapp.usecase

import com.innovaocean.adoptmeapp.repository.BreedRepository
import com.innovaocean.adoptmeapp.util.Resource
import javax.inject.Inject

class GetBreedsUseCase @Inject constructor(private val repository: BreedRepository) {

    suspend fun execute(query: String): GetBreedsResource {
        return when (val response = repository.searchForBreeds(query)) {
            is Resource.Success -> GetBreedsResource.Success(response.breeds)
            Resource.EmptyList -> GetBreedsResource.Error("No kitty cat found")
            Resource.ResponseError -> GetBreedsResource.Error("Unknown Error!")
            Resource.ResponseUnsuccessful -> GetBreedsResource.Error("Request failed! Please try again.")
            Resource.Error -> GetBreedsResource.Error("Something went wrong!")
        }
    }
}