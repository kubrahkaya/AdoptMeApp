package com.innovaocean.adoptmeapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovaocean.adoptmeapp.data.BreedResponse
import com.innovaocean.adoptmeapp.usecase.GetBreedsUseCase
import com.innovaocean.adoptmeapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: GetBreedsUseCase) : ViewModel() {

    private var _searchBreeds: MutableLiveData<Resource<List<BreedResponse>>> = MutableLiveData()
    val searchBreeds: LiveData<Resource<List<BreedResponse>>> = _searchBreeds

    fun searchBreeds(searchQuery: String) {
        if (searchQuery.isEmpty()) {
            return
        }

        viewModelScope.launch {
            _searchBreeds.postValue(Resource.loading(null))
            val response = useCase.execute(searchQuery)
            _searchBreeds.postValue(response)
        }
    }

}