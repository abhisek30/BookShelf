package com.abhisek.project.bookshelf.data.remote.dto


import com.google.gson.annotations.SerializedName

data class BookDto(
    @SerializedName("id") val id: String,
    @SerializedName("image") val image: String,
    @SerializedName("popularity") val popularity: Int,
    @SerializedName("publishedChapterDate") val publishedChapterDate: Int,
    @SerializedName("score") val score: Double,
    @SerializedName("title") val title: String
)
