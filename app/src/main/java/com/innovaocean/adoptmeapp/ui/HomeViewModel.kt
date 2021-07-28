package com.innovaocean.adoptmeapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovaocean.adoptmeapp.usecase.GetBreedsResource
import com.innovaocean.adoptmeapp.usecase.GetBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: GetBreedsUseCase) : ViewModel() {

    private var _searchBreeds: MutableLiveData<BreedEvent> = MutableLiveData()
    val searchBreeds: LiveData<BreedEvent> = _searchBreeds

    var searchedQuery: String = ""

    fun searchBreeds(searchQuery: String) {
        if (searchQuery.isNotEmpty()) {
            _searchBreeds.postValue(BreedEvent.Loading)
            viewModelScope.launch {
                when (val response = useCase.execute(searchQuery)) {
                    is GetBreedsResource.Success -> {
                        _searchBreeds.postValue(BreedEvent.Success(response.breeds))
                    }
                    is GetBreedsResource.Error -> {
                        _searchBreeds.postValue(BreedEvent.Error(response.message))
                    }
                }
            }
        }
    }
}