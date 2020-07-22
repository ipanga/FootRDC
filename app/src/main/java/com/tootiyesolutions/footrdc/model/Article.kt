package com.tootiyesolutions.footrdc.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Article(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("content")
    val content: Content?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("jetpack_featured_media_url")
    val urlToImage: String?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("title")
    val title: Title?
) : Serializable