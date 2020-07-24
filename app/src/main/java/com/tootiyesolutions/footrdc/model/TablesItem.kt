package com.tootiyesolutions.footrdc.model


import com.google.gson.annotations.SerializedName

data class TablesItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: Title?,
    @SerializedName("data")
    val data: Map<String, Tables>
)