package com.innovaocean.adoptmeapp.repository

import com.innovaocean.adoptmeapp.api.PetApi
import com.innovaocean.adoptmeapp.data.BreedResponse
import com.innovaocean.adoptmeapp.data.ImageResponse
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class BreedRepositoryTest {

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

    private val api : PetApi = mock()
    private lateinit var repository: BreedRepositoryImpl

    @Before
    fun setup(){
        repository = BreedRepositoryImpl(api)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when get breeds from repository, getBreeds is called in api service`(){
        runBlockingTest {
            whenever(api.getBreeds()).thenReturn(Response.success(list))

            repository.searchForBreeds("Siamese")

            verify(api).getBreeds()
        }
    }
}