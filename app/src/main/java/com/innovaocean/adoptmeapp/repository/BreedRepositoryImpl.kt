package com.innovaocean.adoptmeapp.repository

import com.innovaocean.adoptmeapp.api.PetApi
import com.innovaocean.adoptmeapp.repository.BreedRepository.BreedResponse.*
import javax.inject.Inject

class BreedRepositoryImpl @Inject constructor(
    private val petApi: PetApi
) : BreedRepository {

    override suspend fun searchForBreeds(searchQuery: String): BreedRepository.BreedResponse {
        return try {
            val response = petApi.getBreeds()
            when {
                response.isSuccessful -> {
                    response.body()?.let { breeds ->
                        val filteredList = breeds.filter { response ->
                            response.name.contains(
                                searchQuery,
                                ignoreCase = true
                            )
                        }
                        return if (filteredList.isEmpty()) {
                            EmptyList
                        } else {
                            Success(filteredList.mapToDomain())
                        }
                    } ?: ResponseError
                }
                else -> ResponseUnsuccessful
            }
        } catch (e: Exception) {
            Error
        }
    }

}