package com.innovaocean.adoptmeapp.util

import com.innovaocean.adoptmeapp.domain.Breed

sealed class Resource {
    data class Success(val breeds: List<Breed>) : Resource()
    object EmptyList : Resource()
    object ResponseError : Resource()
    object ResponseUnsuccessful : Resource()
    object Error : Resource()
}
