package com.innovaocean.adoptmeapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.innovaocean.adoptmeapp.MainCoroutineRule
import com.innovaocean.adoptmeapp.TestDataProvider
import com.innovaocean.adoptmeapp.data.ImageResponse
import com.innovaocean.adoptmeapp.domain.Breed
import com.innovaocean.adoptmeapp.usecase.GetBreedsResource
import com.innovaocean.adoptmeapp.usecase.GetBreedsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel

    @RelaxedMockK
    private lateinit var useCase: GetBreedsUseCase

    private val testBreedsList = TestDataProvider.getBreeds()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        // instantiate the system in test
        viewModel = HomeViewModel(useCase)
    }

    @Test
    fun `when searched breed is in repo`() {
        val existedBreedName = "Siamese"
        val breeds = mutableListOf<Breed>()
        breeds.add(
            Breed(
                id = "1",
                name = "Siamese",
                image = ImageResponse(""),
                temperament = "Good cat",
                wikipediaUrl = "www",
                energyLevel = 5
            )
        )
        mainCoroutineRule.runBlockingTest {
            //arrange
            coEvery {
                useCase.execute(existedBreedName)
            } returns GetBreedsResource.Success(testBreedsList)

            //act
            viewModel.searchBreeds(existedBreedName)

            //assert
            assertNotNull(viewModel.searchBreeds.value)
            assertEquals(viewModel.searchBreeds.value, BreedEvent.Success(breeds))
        }
    }

    @Test
    fun `when searched breed is not repo`() {
        val unknownBreed = "XXX"
        mainCoroutineRule.runBlockingTest {
            //arrange
            coEvery {
                useCase.execute(unknownBreed)
            } returns GetBreedsResource.Error("Error")

            //act
            viewModel.searchBreeds(unknownBreed)

            //assert
            assertEquals(viewModel.searchBreeds.value, BreedEvent.Error("Error"))
        }
    }
}