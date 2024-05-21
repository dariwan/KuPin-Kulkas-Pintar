package com.dariwan.kupin.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecommendationResponse (
    @SerializedName("recommendations")
    val recommendations: List<RecipeItem>
)

data class RecipeItem(
    @SerializedName("bahan")
    val bahan: String,

    @SerializedName("step")
    val step: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String,
)