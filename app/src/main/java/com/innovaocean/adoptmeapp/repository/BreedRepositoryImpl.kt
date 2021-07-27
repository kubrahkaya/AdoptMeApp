package com.innovaocean.adoptmeapp.repository

import com.innovaocean.adoptmeapp.api.PetApi
import com.innovaocean.adoptmeapp.data.BreedResponse
import com.innovaocean.adoptmeapp.util.Resource
import java.lang.Exception
import javax.inject.Inject

class BreedRepositoryImpl @Inject constructor(
    private val petApi: PetApi
) : BreedRepository {

    override suspend fun searchForBreeds(searchQuery: String): Resource<List<BreedResponse>> {
        return try {
            val response = petApi.getBreeds()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it.filter { response ->
                        response.name.contains(searchQuery)
                    })
                } ?: Resource.error("Unknown error", null)
            } else {
                Resource.error("Unknown error", null)
            }
        } catch (e: Exception) {
            Resource.error("Please check network connection", null)
        }
    }

}