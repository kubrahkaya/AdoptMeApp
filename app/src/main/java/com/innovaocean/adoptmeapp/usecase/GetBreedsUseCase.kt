package com.innovaocean.adoptmeapp.usecase

import com.innovaocean.adoptmeapp.R
import com.innovaocean.adoptmeapp.repository.BreedRepository
import com.innovaocean.adoptmeapp.util.Resource
import com.innovaocean.adoptmeapp.util.StringResourceWrapper
import javax.inject.Inject

class GetBreedsUseCase @Inject constructor(
    private val repository: BreedRepository,
    private val strings: StringResourceWrapper
) {

    suspend fun execute(query: String): GetBreedsResource {
        return when (val response = repository.searchForBreeds(query)) {
            is Resource.Success -> GetBreedsResource.Success(response.breeds)
            Resource.EmptyList -> GetBreedsResource.Error(strings.getString(R.string.no_cat_found))
            Resource.ResponseError -> GetBreedsResource.Error(strings.getString(R.string.unknown_error))
            Resource.ResponseUnsuccessful -> GetBreedsResource.Error(strings.getString(R.string.request_failed))
            Resource.Error -> GetBreedsResource.Error(strings.getString(R.string.something_went_wrong))
        }
    }
}