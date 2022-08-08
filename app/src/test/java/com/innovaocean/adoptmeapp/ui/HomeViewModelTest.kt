package com.innovaocean.adoptmeapp.ui

import com.innovaocean.adoptmeapp.TestDataProvider
import com.innovaocean.adoptmeapp.ViewModelFlowCollector
import com.innovaocean.adoptmeapp.domain.Breed
import com.innovaocean.adoptmeapp.domain.Image
import com.innovaocean.adoptmeapp.usecase.GetBreedsResource
import com.innovaocean.adoptmeapp.usecase.GetBreedsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val dispatcher = TestCoroutineDispatcher()

    private val useCase = mockk<GetBreedsUseCase>(relaxUnitFun = true)

    private val viewModel = HomeViewModel(useCase, dispatcher)
    private val collector = ViewModelFlowCollector(viewModel.state, viewModel.events, dispatcher)

    private val testBreedsList = TestDataProvider.getBreeds()

    @Test
    fun `when searched breed is in repo`() {
        collector.test { states, _ ->
            val existedBreedName = "Siamese"
            val breeds = mutableListOf<Breed>()
            breeds.add(
                Breed(
                    id = "1",
                    name = "Siamese",
                    image = Image(""),
                    temperament = "Good cat",
                    wikipediaUrl = "www",
                    energyLevel = 5
                )
            )
            //arrange
            coEvery {
                useCase.execute(existedBreedName)
            } returns GetBreedsResource.Success(testBreedsList)

            //act
            viewModel.searchBreeds(existedBreedName)

            //assert
            val expectedStates = BreedState(isLoading = false, breedList = breeds)
            assertEquals(expectedStates, states.last())
        }
    }

    @Test
    fun `when searched breed is not repo`() {
        val unknownBreed = "XXX"
        collector.test { _, events ->
            //arrange
            coEvery {
                useCase.execute(unknownBreed)
            } returns GetBreedsResource.Error("Error")

            //act
            viewModel.searchBreeds(unknownBreed)

            //assert
            val expectedEvents = listOf(
                BreedsEvent.ShowError("Error")
            )
            assertEquals(expectedEvents, events)
        }
    }

    @Test
    fun `when user clicks on an item then navigate to detail`() {
        collector.test { _, events ->
            val existedBreedName = "Siamese"
            val breeds = mutableListOf<Breed>()
            val breed = Breed(
                id = "1",
                name = "Siamese",
                image = Image(""),
                temperament = "Good cat",
                wikipediaUrl = "www",
                energyLevel = 5
            )
            breeds.add(breed)
            //arrange
            coEvery {
                useCase.execute(existedBreedName)
            } returns GetBreedsResource.Success(testBreedsList)

            //act
            viewModel.searchBreeds(existedBreedName)
            viewModel.onBreedClicked(breed)

            //assert
            val expectedEvents = listOf<BreedsEvent>(BreedsEvent.OpenBreedDetail(breed))
            assertEquals(expectedEvents, events)
        }
    }
}