package com.abhisek.project.bookshelf.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("country") val country: String? = null,
    @SerializedName("region") val region: String? = null,
)
