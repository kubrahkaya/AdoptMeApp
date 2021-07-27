package com.innovaocean.adoptmeapp.usecase

import com.innovaocean.adoptmeapp.repository.BreedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBreedsUseCase @Inject constructor(private val repository: BreedRepository) {

    suspend fun execute(query: String) =
        withContext(Dispatchers.IO) {
            repository.searchForBreeds(query)
        }
}