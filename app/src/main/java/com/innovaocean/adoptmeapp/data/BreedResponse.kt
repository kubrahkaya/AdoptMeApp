package com.innovaocean.adoptmeapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BreedResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
//    @SerializedName("image")
//    val image: ImageResponse,
    @SerializedName("temperament")
    val temperament: String,
    @SerializedName("wikipedia_url")
    val wikipediaUrl: String,
    @SerializedName("energy_level")
    val energyLevel: Int
): Parcelable