package com.innovaocean.adoptmeapp.repository

import com.innovaocean.adoptmeapp.data.BreedResponse
import com.innovaocean.adoptmeapp.util.Resource

interface BreedRepository  {

    suspend fun searchForBreeds(searchQuery: String): Resource<List<BreedResponse>>

}