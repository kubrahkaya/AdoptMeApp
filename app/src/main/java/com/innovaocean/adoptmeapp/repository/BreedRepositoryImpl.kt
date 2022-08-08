package com.innovaocean.adoptmeapp.repository

import com.innovaocean.adoptmeapp.api.PetApi
import com.innovaocean.adoptmeapp.util.Resource
import javax.inject.Inject

class BreedRepositoryImpl @Inject constructor(
    private val petApi: PetApi,
    private val breedMapper: BreedMapper
) : BreedRepository {

    override suspend fun searchForBreeds(searchQuery: String): Resource {
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
                            Resource.EmptyList
                        } else {
                            Resource.Success(breedMapper.mapToDomain(filteredList))
                        }
                    } ?: Resource.ResponseError
                }
                else -> Resource.ResponseUnsuccessful
            }
        } catch (e: Exception) {
            Resource.Error
        }
    }

}