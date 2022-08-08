package com.innovaocean.adoptmeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovaocean.adoptmeapp.di.IoDispatcher
import com.innovaocean.adoptmeapp.domain.Breed
import com.innovaocean.adoptmeapp.usecase.GetBreedsResource
import com.innovaocean.adoptmeapp.usecase.GetBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetBreedsUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,) : ViewModel() {

    private val _state = MutableStateFlow(
        BreedState(
            breedList = emptyList(),
            isLoading = false
        )
    )

    private val _events = MutableSharedFlow<BreedsEvent>()

    internal val state: Flow<BreedState> = _state
    internal val events: Flow<BreedsEvent> = _events

    var searchedQuery: String = ""

    fun searchBreeds(searchQuery: String) {
        if (searchQuery.isNotEmpty()) {
            viewModelScope.launch(dispatcher) {
                _state.value = _state.value.copy(isLoading = true)
                val result = useCase.execute(searchQuery)
                _state.value = _state.value.copy(isLoading = false)
                when (result) {
                    is GetBreedsResource.Success -> {
                        _state.value = _state.value.copy(breedList = result.breeds)
                    }
                    is GetBreedsResource.Error -> {
                        _state.value = _state.value.copy(breedList = emptyList())
                        _events.emit(BreedsEvent.ShowError(result.message))
                    }
                }
            }
        }
    }

    fun onBreedClicked(breed: Breed) = viewModelScope.launch(dispatcher){
        _events.emit(BreedsEvent.OpenBreedDetail(breed))
    }
}

internal data class BreedState(
    var isLoading: Boolean,
    val breedList: List<Breed>
)

internal sealed class BreedsEvent {
    data class ShowError(val message: String) : BreedsEvent()
    data class OpenBreedDetail(val breed: Breed): BreedsEvent()
}