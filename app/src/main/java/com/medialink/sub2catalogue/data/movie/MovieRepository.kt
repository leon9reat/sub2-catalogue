package com.medialink.sub2catalogue.data.movie

import android.util.Log
import com.google.gson.Gson
import com.medialink.sub2catalogue.OperationCallback
import com.medialink.sub2catalogue.models.errors.ErrorRespon
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
                Log.d("debug failure", t.message)
            }

            override fun onResponse(call: Call<MovieRespon>, response: Response<MovieRespon>) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body()?.results)
                    Log.d("debug success", "movie total results: ${response.body()?.totalResults}")
                } else {
                    Log.d("debug error", "test")

                    val jsonString = response.errorBody()?.charStream()?.readText() ?: "{}"
                    val error400 = Gson().fromJson(jsonString, ErrorRespon::class.java)
                    when (response.code()) {
                        401 -> callback.onError(error400.statusMessage)
                        404 -> callback.onError(error400.statusMessage)
                        else -> {
                            callback.onError(jsonString)
                        }
                    }
                }
            }

        })
    }

    override fun cancel() {
        call?.let { it.cancel() }
    }
}