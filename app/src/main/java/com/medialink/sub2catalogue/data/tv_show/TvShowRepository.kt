package com.medialink.sub2catalogue.data.tv_show

import com.google.gson.Gson
import com.medialink.sub2catalogue.OperationCallback
import com.medialink.sub2catalogue.models.errors.ErrorRespon
import com.medialink.sub2catalogue.models.tv_show.TvShowRespon
import com.medialink.sub2catalogue.network.ApiFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowRepository : TvShowDataSource {
    override var page: Int = 1
    override var language: String = "en-US"

    private var call: Call<TvShowRespon>? = null

    override fun retriveTvShow(callback: OperationCallback) {
        call = ApiFactory.apiTvShow.getTvShowPopular(page, language)
        call?.enqueue(object : Callback<TvShowRespon> {
            override fun onFailure(call: Call<TvShowRespon>, t: Throwable) {
                callback.onError(t)
            }

            override fun onResponse(call: Call<TvShowRespon>, response: Response<TvShowRespon>) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body()?.results)
                } else {
                    val jsonString = response.errorBody()?.charStream()?.readText() ?: "{}"
                    val error400 = Gson().fromJson(jsonString, ErrorRespon::class.java)
                    when (response.code()) {
                        401 -> callback.onError(error400.statusMessage)
                        404 -> callback.onError(error400.statusMessage)
                        else -> callback.onError(jsonString)
                    }
                    callback.onError(response.errorBody())
                }
            }
        })
    }

    override fun cancel() {
        call?.cancel()
    }
}