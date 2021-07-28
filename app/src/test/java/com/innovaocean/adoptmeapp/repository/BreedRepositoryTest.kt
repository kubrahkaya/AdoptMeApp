package com.innovaocean.adoptmeapp.repository

import com.innovaocean.adoptmeapp.TestDataProvider
import com.innovaocean.adoptmeapp.api.PetApi
import com.innovaocean.adoptmeapp.util.Resource
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.lang.Exception

@ExperimentalCoroutinesApi
class BreedRepositoryTest {

    private val testBreedsList = TestDataProvider.getBreeds()
    private val testBreedsResponse = TestDataProvider.getBreedsResponse()

    @MockK
    private lateinit var api: PetApi

    @MockK
    private lateinit var mapper: BreedMapper
    private lateinit var repository: BreedRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = BreedRepositoryImpl(api, mapper)
    }

    @Test
    fun `when api called and breed is in found then confirm Success`() {
        runBlockingTest {
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
        runBlockingTest {
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
        runBlockingTest {
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
        runBlockingTest {
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
        runBlockingTest {
            //arrange
            coEvery { api.getBreeds() } throws Exception("error")

            //act
            val response = repository.searchForBreeds("XX")

            //assert
            assertEquals(response, Resource.Error)
        }
    }
}