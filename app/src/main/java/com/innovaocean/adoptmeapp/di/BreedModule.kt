package com.innovaocean.adoptmeapp.di

import com.innovaocean.adoptmeapp.data.repository.BreedRepository
import com.innovaocean.adoptmeapp.data.repository.BreedRepositoryImpl
import com.innovaocean.adoptmeapp.domain.usecase.GetBreedsUseCase
import com.innovaocean.adoptmeapp.domain.usecase.GetBreedsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class BreedModule {

    @ViewModelScoped
    @Binds
    abstract fun bindUseCase(useCaseImpl: GetBreedsUseCaseImpl): GetBreedsUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindRepository(repositoryImpl: BreedRepositoryImpl): BreedRepository

}