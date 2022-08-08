package com.innovaocean.adoptmeapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Breed(
    val id: String,
    val name: String,
    val image: Image?,
    val temperament: String,
    val wikipediaUrl: String?,
    val energyLevel: Int
) : Parcelable