package com.innovaocean.adoptmeapp.domain.usecase

import com.innovaocean.adoptmeapp.domain.Breed

sealed class GetBreedsResult {
    data class Success(val breeds: List<Breed>) : GetBreedsResult()
    data class Error(val message: String) : GetBreedsResult()
}
