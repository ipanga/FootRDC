package com.tootiyesolutions.footrdc.model


import com.google.gson.annotations.SerializedName


data class Result(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("date")
    val resultDate: String?,
    @SerializedName("main_results")
    val resultScores: List<String>?,
    @SerializedName("status")
    val resultStatus: String?,
    @SerializedName("title")
    val resultTitle: Title?
)