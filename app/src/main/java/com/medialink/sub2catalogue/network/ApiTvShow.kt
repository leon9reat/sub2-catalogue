package com.medialink.sub2catalogue.network

import com.medialink.sub2catalogue.models.tv_show.TvShowRespon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiTvShow {
    @GET("tv/popular")
    fun getTvShowPopular(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Call<TvShowRespon>
}