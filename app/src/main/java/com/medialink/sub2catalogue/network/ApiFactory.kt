package com.medialink.sub2catalogue.network

object ApiFactory {
    val apiMovie: ApiMovie = RetrofitClient.retrofit().create(ApiMovie::class.java)
    val apiTvShow: ApiTvShow = RetrofitClient.retrofit().create(ApiTvShow::class.java)
}