package com.dariwan.kupin.core.data.remote.request

import com.google.gson.annotations.SerializedName

data class MaterialRequest (
    @SerializedName("user_input")
    val user_input: String,
)