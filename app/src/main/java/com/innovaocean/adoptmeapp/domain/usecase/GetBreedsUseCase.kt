package com.innovaocean.adoptmeapp.domain.usecase

import com.innovaocean.adoptmeapp.R
import com.innovaocean.adoptmeapp.data.repository.BreedRepository
import com.innovaocean.adoptmeapp.data.repository.BreedRepository.BreedResponse
import com.innovaocean.adoptmeapp.util.StringResourceWrapper
import javax.inject.Inject

interface GetBreedsUseCase{

    suspend operator fun invoke(query: String): GetBreedsResult
}

class GetBreedsUseCaseImpl @Inject constructor(
    private val repository: BreedRepository,
    private val strings: StringResourceWrapper
) : GetBreedsUseCase {

    override suspend fun invoke(query: String): GetBreedsResult {
        return when (val response = repository.searchForBreeds(query)) {
            is BreedResponse.Success -> GetBreedsResult.Success(response.breeds)
            BreedResponse.EmptyList -> GetBreedsResult.Error(strings.getString(R.string.no_cat_found))
            BreedResponse.ResponseError -> GetBreedsResult.Error(strings.getString(R.string.unknown_error))
            BreedResponse.ResponseUnsuccessful -> GetBreedsResult.Error(strings.getString(R.string.request_failed))
            BreedResponse.Error -> GetBreedsResult.Error(strings.getString(R.string.something_went_wrong))
        }
    }
}