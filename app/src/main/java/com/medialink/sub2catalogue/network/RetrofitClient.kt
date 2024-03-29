package com.medialink.sub2catalogue.network

import com.google.gson.Gson
import com.medialink.sub2catalogue.BuildConfig
import com.medialink.sub2catalogue.Consts
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", Consts.tmdbApiKey)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client =
        if (BuildConfig.DEBUG) {
            OkHttpClient().newBuilder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()
        }

    private val gson = Gson().newBuilder()
        .setLenient()
        .create()

    fun retrofit(): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(Consts.TMDB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}