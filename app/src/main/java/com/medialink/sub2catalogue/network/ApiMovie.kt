package com.medialink.sub2catalogue.network

import com.medialink.sub2catalogue.models.movie.MovieRespon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMovie {
    @GET("movie/popular")
    fun getMoviePopular(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Call<MovieRespon>
}