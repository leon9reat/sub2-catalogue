package com.medialink.sub2catalogue.ui.tv_show

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.medialink.sub2catalogue.Consts
import com.medialink.sub2catalogue.R
import com.medialink.sub2catalogue.models.tv_show.TvShow
import kotlinx.android.synthetic.main.tv_show_item.view.*

class TvShowAdapter(
    private var tvShows: List<TvShow>,
    private var itemClickListener: ItemClickListener
) : RecyclerView.Adapter<TvShowAdapter.MyViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(tvShow: TvShow)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShow: TvShow, clickListener: ItemClickListener) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load("${ Consts.TMDB_PHOTO_URL}${tvShow.posterPath}")
                    .override(120, 160)
                    .into(img_poster_tv_show)
                progress_vote_tv_show.progress = tvShow.voteAverage?.times(10)?.toInt() ?: 0
                tv_vote_tv_show.text = "${progress_vote_tv_show.progress}%"


                tv_title_tv_show.text = tvShow.name
                tv_airing_date.text = tvShow.firstAirDate
                tv_overview_tv_show.text = tvShow.overview
                itemView.setOnClickListener {
                    clickListener.onItemClick(tvShow)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tv_show_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = tvShows.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(tvShows[position], itemClickListener)
    }

    fun update(data: List<TvShow>) {
        if (data.isNotEmpty()) {
            tvShows = data
            notifyDataSetChanged()
        }
    }
}