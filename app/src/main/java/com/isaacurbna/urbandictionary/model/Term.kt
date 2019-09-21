package com.isaacurbna.urbandictionary.model

import com.google.gson.annotations.SerializedName

@Suppress("ArrayInDataClass")
data class Term(
    @SerializedName("defid")
    val id: String,
    val definition: String?,
    val author: String?,
    val word: String?,
    val example: String?,
    @SerializedName("written_on")
    val writtenOn: String?,
    val permalink: String?,
    @SerializedName("current_vote")
    val currentVote: String?,
    @SerializedName("thumbs_up")
    val thumbsUp: Int?,
    @SerializedName("thumbs_down")
    val thumbsDown: Int?,
    @SerializedName("sound_urls")
    val soundUrls: Array<SoundUrl>
)