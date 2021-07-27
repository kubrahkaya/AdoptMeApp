package com.innovaocean.adoptmeapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageResponse(
    @SerializedName("url")
    val url: String
): Parcelable