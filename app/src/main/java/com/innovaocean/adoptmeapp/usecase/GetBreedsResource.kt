package com.innovaocean.adoptmeapp.usecase

import com.innovaocean.adoptmeapp.domain.Breed

sealed class GetBreedsResource {
    data class Success(val breeds: List<Breed>) : GetBreedsResource()
    data class Error(val message: String) : GetBreedsResource()
}
