package com.innovaocean.adoptmeapp.di

import android.content.Context
import com.innovaocean.adoptmeapp.util.StringResourceWrapper
import com.innovaocean.adoptmeapp.util.StringResourceWrapperAndroid
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object StringResourceModule {

    @ViewModelScoped
    @Provides
    fun provideStringResources(
        @ApplicationContext context: Context
    ): StringResourceWrapper = StringResourceWrapperAndroid(context)

}