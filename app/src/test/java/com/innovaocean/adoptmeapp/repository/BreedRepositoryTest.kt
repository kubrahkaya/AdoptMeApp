package com.innovaocean.adoptmeapp.repository

import com.innovaocean.adoptmeapp.TestDataProvider
import com.innovaocean.adoptmeapp.api.PetApi
import com.innovaocean.adoptmeapp.util.Status
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class BreedRepositoryTest {

    private val testBreedsList = TestDataProvider.getBreeds()

    @MockK
    private lateinit var api: PetApi
    private lateinit var repository: BreedRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = BreedRepositoryImpl(api)
    }

    @Test
    fun `when api called and breed is in found then confirm Success`() {
        runBlockingTest {
            //arrange
            coEvery { api.getBreeds() } returns Response.success(testBreedsList)

            //act
            val response = repository.searchForBreeds("Siamese")

            //assert
            assertEquals(response.status, Status.SUCCESS)
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
            assertEquals(response.status, Status.ERROR)
        }
    }
}