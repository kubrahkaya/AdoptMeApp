package com.innovaocean.adoptmeapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.innovaocean.adoptmeapp.MainCoroutineRule
import com.innovaocean.adoptmeapp.TestDataProvider
import com.innovaocean.adoptmeapp.usecase.GetBreedsUseCase
import com.innovaocean.adoptmeapp.util.Resource
import com.innovaocean.adoptmeapp.util.Status
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
        mainCoroutineRule.runBlockingTest {
            //arrange
            coEvery {
                useCase.execute(existedBreedName)
            } returns Resource.success(testBreedsList)

            //act
            viewModel.searchBreeds(existedBreedName)

            //assert
            assertNotNull(viewModel.searchBreeds.value)
            assertEquals(viewModel.searchBreeds.value?.status, Status.SUCCESS)
        }
    }

    @Test
    fun `when searched breed is not repo`() {
        val unknownBreed = "XXX"
        mainCoroutineRule.runBlockingTest {
            //arrange
            coEvery {
                useCase.execute(unknownBreed)
            } returns Resource.error("", null)

            //act
            viewModel.searchBreeds(unknownBreed)

            //assert
            assertEquals(viewModel.searchBreeds.value?.status, Status.ERROR)
        }
    }
}