package com.medialink.sub2catalogue.data.movie

import com.medialink.sub2catalogue.OperationCallback

interface MovieDataSource {
    var page: Int
    var language: String
    fun retriveMovie(callback: OperationCallback)
    fun cancel()
}