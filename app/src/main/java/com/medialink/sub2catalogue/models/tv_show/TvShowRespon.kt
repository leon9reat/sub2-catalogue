package com.medialink.sub2catalogue.models.tv_show

import com.google.gson.annotations.SerializedName

data class TvShowRespon(

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<TvShow?>? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
)