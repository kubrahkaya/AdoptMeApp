package com.innovaocean.adoptmeapp.di

import com.innovaocean.adoptmeapp.api.PetApi
import com.innovaocean.adoptmeapp.repository.BreedRepository
import com.innovaocean.adoptmeapp.repository.BreedRepositoryImpl
import com.innovaocean.adoptmeapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePetApi(): PetApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PetApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(api: PetApi) = BreedRepositoryImpl(api) as BreedRepository
}