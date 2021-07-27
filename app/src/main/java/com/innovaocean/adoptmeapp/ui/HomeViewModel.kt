package com.innovaocean.adoptmeapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovaocean.adoptmeapp.data.BreedResponse
import com.innovaocean.adoptmeapp.usecase.GetBreedsUseCase
import com.innovaocean.adoptmeapp.util.Resource
import com.innovaocean.adoptmeapp.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: GetBreedsUseCase) : ViewModel() {

    private var _searchBreeds: MutableLiveData<Resource<List<BreedResponse>>> = MutableLiveData()
    val searchBreeds: LiveData<Resource<List<BreedResponse>>> = _searchBreeds

    var searchedQuery: String = ""

    fun searchBreeds(searchQuery: String) {
        if (searchQuery.isEmpty()) {
            return
        }

        _searchBreeds.postValue(Resource.loading(null))
        viewModelScope.launch {
            val response = useCase.execute(searchQuery)
            if (response.status == Status.SUCCESS) {
                _searchBreeds.postValue(Resource.success(response.data))
            } else {
                _searchBreeds.postValue(Resource.error(msg = "No breed found", null))
            }
        }
    }

}