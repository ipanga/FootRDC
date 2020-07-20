package com.tootiyesolutions.footrdc.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "articles"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
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