package com.medialink.sub2catalogue.data.movie

import android.util.Log
import com.medialink.sub2catalogue.OperationCallback
import com.medialink.sub2catalogue.models.movie.MovieRespon
import com.medialink.sub2catalogue.network.ApiFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository : MovieDataSource {

    override var page = 1
    override var language = "en-US"

    private var call: Call<MovieRespon>? = null

    override fun retriveMovie(callback: OperationCallback) {
        call = ApiFactory.apiMovie.getMoviePopular(page, language)
        call?.enqueue(object : Callback<MovieRespon> {
            override fun onFailure(call: Call<MovieRespon>, t: Throwable) {
                callback.onError(t)
            }

            override fun onResponse(call: Call<MovieRespon>, response: Response<MovieRespon>) {
                response.body()?.let {
                    if (response.isSuccessful) {
                        callback.onSuccess(it.results)
                        Log.d("debug", "movie total results: ${it.totalResults}")
                    } else {
                        callback.onError(response.errorBody())
                        Log.d("debug", response.errorBody().toString())
                    }
                }
            }

        })
    }

    override fun cancel() {
        call?.let { it.cancel() }
    }
}