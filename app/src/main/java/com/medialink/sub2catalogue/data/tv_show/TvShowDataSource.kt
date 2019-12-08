package com.medialink.sub2catalogue.data.tv_show

import com.medialink.sub2catalogue.OperationCallback

interface TvShowDataSource {
    var page: Int
    var language: String

    fun retriveTvShow(callback: OperationCallback)
    fun cancel()
}