package com.innovaocean.adoptmeapp.usecase

import com.innovaocean.adoptmeapp.R
import com.innovaocean.adoptmeapp.TestDataProvider
import com.innovaocean.adoptmeapp.repository.BreedRepository
import com.innovaocean.adoptmeapp.util.Resource
import com.innovaocean.adoptmeapp.util.StringResourceWrapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetBreedsUseCaseTest {

    private val testBreedList = TestDataProvider.getBreeds()

    private val breedRepository = mockk<BreedRepository>()
    private val strings = mockk<StringResourceWrapper>()
    private val getBreedsUseCase = GetBreedsUseCase(breedRepository, strings)


    @Before
    fun setUp() {
        every { strings.getString(R.string.request_failed)} returns "Request failed! Please try again."
        every { strings.getString(R.string.unknown_error)} returns "Unknown Error!"
        every { strings.getString(R.string.something_went_wrong)} returns "Something went wrong!"
    }

    @Test
    fun `when api called and breed is found then confirm Success`() {
        runBlockingTest {
            //arrange
            coEvery { breedRepository.searchForBreeds("Siamese") } returns Resource.Success(
                testBreedList
            )

            //act
            val response = getBreedsUseCase.execute("Siamese")

            //assert
            assertEquals(response, GetBreedsResource.Success(testBreedList))
        }
    }

    @Test
    fun `when api called and returns error then confirm Failure`() {
        runBlockingTest {
            //arrange
            coEvery {
                breedRepository.searchForBreeds("XX")
            } returns Resource.ResponseUnsuccessful

            //act
            val response = getBreedsUseCase.execute("XX")

            //assert
            assertEquals(
                response,
                GetBreedsResource.Error("Request failed! Please try again.")
            )
        }
    }

    @Test
    fun `when repo doesn't contain searched query return error`() {
        runBlockingTest {
            //arrange
            coEvery { breedRepository.searchForBreeds("XX") } returns Resource.ResponseError

            //act
            val response = getBreedsUseCase.execute("XX")

            //assert
            assertEquals(response, GetBreedsResource.Error("Unknown Error!"))
        }
    }

    @Test
    fun `when api error query returns generic error`() {
        runBlockingTest {
            //arrange
            coEvery { breedRepository.searchForBreeds("XX") } returns Resource.Error

            //act
            val response = getBreedsUseCase.execute("XX")

            //assert
            assertEquals(response, GetBreedsResource.Error("Something went wrong!"))
        }
    }
}