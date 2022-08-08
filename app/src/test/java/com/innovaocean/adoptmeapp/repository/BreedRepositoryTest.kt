package com.innovaocean.adoptmeapp.repository

import com.innovaocean.adoptmeapp.TestDataProvider
import com.innovaocean.adoptmeapp.api.PetApi
import com.innovaocean.adoptmeapp.util.Resource
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class BreedRepositoryTest {

    private val testBreedsList = TestDataProvider.getBreeds()
    private val testBreedsResponse = TestDataProvider.getBreedsResponse()

    private val api= mockk<PetApi>()
    private val mapper = mockk<BreedMapper>()
    private val repository = BreedRepositoryImpl(api, mapper)

    @Test
    fun `when api called and breed is in found then confirm Success`() {
        runBlocking {
            //arrange
            coEvery { api.getBreeds() } returns Response.success(testBreedsResponse)
            every { mapper.mapToDomain(testBreedsResponse) } returns testBreedsList

            //act
            val response = repository.searchForBreeds("Siamese")

            //assert
            assertEquals(response, Resource.Success(testBreedsList))
        }
    }

    @Test
    fun `when api called and breed is empty then returns empty`() {
        runBlocking {
            //arrange
            coEvery { api.getBreeds() } returns Response.success(emptyList())

            //act
            val response = repository.searchForBreeds("Siamese")

            //assert
            assertEquals(response, Resource.EmptyList)
        }
    }

    @Test
    fun `when api called and returns error then confirm Failure`() {
        runBlocking {
            //arrange
            coEvery { api.getBreeds().isSuccessful } returns false

            //act
            val response = repository.searchForBreeds("XX")

            //assert
            assertEquals(response, Resource.ResponseUnsuccessful)
        }
    }

    @Test
    fun `when repo doesn't contain searched query return error`() {
        runBlocking {
            //arrange
            coEvery { api.getBreeds().body() } returns TestDataProvider.getBreedsResponse()

            //act
            val response = repository.searchForBreeds("XX")

            //assert
            assertEquals(response, Resource.Error)
        }
    }

    @Test
    fun `when api error query returns generic error`() {
        runBlocking {
            //arrange
            coEvery { api.getBreeds() } throws Exception("error")

            //act
            val response = repository.searchForBreeds("XX")

            //assert
            assertEquals(response, Resource.Error)
        }
    }
}