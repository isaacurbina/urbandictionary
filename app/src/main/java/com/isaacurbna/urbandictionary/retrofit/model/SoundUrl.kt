package com.isaacurbna.urbandictionary.retrofit.model


import com.google.gson.annotations.SerializedName

// TODO(create custom serializer to parse object and ignore custom incremental key)
data class SoundUrl(
    @SerializedName("0")
    var x0: String?,
    @SerializedName("1")
    var x1: String?,
    @SerializedName("2")
    var x2: String?
)