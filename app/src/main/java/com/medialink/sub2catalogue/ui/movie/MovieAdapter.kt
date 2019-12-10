package com.medialink.sub2catalogue.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.medialink.sub2catalogue.Consts
import com.medialink.sub2catalogue.R
import com.medialink.sub2catalogue.models.movie.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter(
    private var movies: List<Movie>,
    private val itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, clickListener: ItemClickListener) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load("${Consts.TMDB_PHOTO_URL}${movie.posterPath}")
                    .override(120, 160)
                    .into(img_poster_tv_show)

                percent_vote_average.progress = movie.voteAverage?.times(10)?.toInt() ?: 0
                tv_vote_average.text = "${percent_vote_average.progress}%"

                tv_title_tv_show.text = movie.title
                tv_release_date.text = movie.releaseDate
                tv_overview.text = movie.overview
                tv_more_info.setOnClickListener {
                    clickListener.onItemClicked(movie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(movies[position], itemClickListener)
    }

    fun update(data: List<Movie>) {
        if (data.isNotEmpty()) {
            movies = data as ArrayList<Movie>
            notifyDataSetChanged()
        }
    }

    interface ItemClickListener {
        fun onItemClicked(movie: Movie)
    }
}
