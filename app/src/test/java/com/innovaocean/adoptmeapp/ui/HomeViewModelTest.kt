package com.innovaocean.adoptmeapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.innovaocean.adoptmeapp.MainCoroutineRule
import com.innovaocean.adoptmeapp.data.BreedResponse
import com.innovaocean.adoptmeapp.data.ImageResponse
import com.innovaocean.adoptmeapp.repository.BreedRepository
import com.innovaocean.adoptmeapp.usecase.GetBreedsUseCase
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var repository: BreedRepository

    private lateinit var viewModel: HomeViewModel

    private lateinit var useCase: GetBreedsUseCase

    private val list = listOf(
        BreedResponse(
            id = "1",
            name = "Siamese",
            image = ImageResponse(""),
            temperament = "Good cat",
            wikipediaUrl = "www",
            energyLevel = 6
        )
    )

    @Before
    fun setUp() {
        useCase = GetBreedsUseCase(repository)

        // instantiate the system in test
        viewModel = HomeViewModel(useCase)
    }

    @Test
    fun `when searched breed is in repo`() {
        mainCoroutineRule.runBlockingTest {
            whenever(repository.searchForBreeds("Siamese")).thenAnswer {
                list
            }

            viewModel.searchBreeds("Siamese")

            Assert.assertNotNull(viewModel.searchBreeds.value)
        }
    }

}