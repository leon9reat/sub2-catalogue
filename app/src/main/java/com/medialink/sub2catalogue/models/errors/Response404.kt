package com.medialink.sub2catalogue.models.errors

import com.google.gson.annotations.SerializedName

data class Response404(

    @field:SerializedName("status_message")
    val statusMessage: String? = null,

    @field:SerializedName("status_code")
    val statusCode: Int? = null
)