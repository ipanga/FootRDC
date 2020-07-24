package com.tootiyesolutions.footrdc.model


import com.google.gson.annotations.SerializedName

data class Tables(
    @SerializedName("bc")
    val bc: String?,
    @SerializedName("bp")
    val bp: String?,
    @SerializedName("gd")
    val gd: String?,
    @SerializedName("mg")
    val mg: String?,
    @SerializedName("mj")
    val mj: String?,
    @SerializedName("mn")
    val mn: String?,
    @SerializedName("mp")
    val mp: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("pos")
    val pos: String?,
    @SerializedName("pts")
    val pts: String?
)