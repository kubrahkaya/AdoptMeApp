package com.innovaocean.adoptmeapp.data

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("image")
    val image: String
)