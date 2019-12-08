package com.medialink.sub2catalogue.data.tv_show

import com.medialink.sub2catalogue.OperationCallback
import com.medialink.sub2catalogue.models.tv_show.TvShowRespon
import retrofit2.Call

class TvShowRepository: TvShowDataSource {
    override var page: Int
        get() = 1
        set(value) {}
    override var language: String
        get() = "en-US"
        set(value) {}

    private var call: Call<TvShowRespon>? = null

    override fun retriveTvShow(callback: OperationCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}