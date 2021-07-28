package com.innovaocean.adoptmeapp.domain

import android.os.Parcelable
import com.innovaocean.adoptmeapp.data.ImageResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Breed(
    val id: String,
    val name: String,
    val image: ImageResponse?,
    val temperament: String,
    val wikipediaUrl: String,
    val energyLevel: Int
) : Parcelable