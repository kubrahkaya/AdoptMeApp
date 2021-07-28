package com.innovaocean.adoptmeapp.ui

import com.innovaocean.adoptmeapp.domain.Breed

sealed class BreedEvent {
    data class Success(val list: List<Breed>) : BreedEvent()
    data class Error(val error: String) : BreedEvent()
    object Loading : BreedEvent()
}