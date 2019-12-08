package com.medialink.sub2catalogue.ui.movie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.medialink.sub2catalogue.Consts
import com.medialink.sub2catalogue.R
import com.medialink.sub2catalogue.models.movie.Movie
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.layout_detail_description.*

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: Movie = MovieDetailFragmentArgs.fromBundle(arguments as Bundle).movie
        tv_title.text = args.title
        tv_release.text = args.releaseDate
        val i = args.voteAverage?.times(10)?.toInt()
        tv_vote.text = "${i}%"
        tv_overview.text = args.overview
        Glide.with(this)
            .load("${Consts.TMDB_PHOTO_URL2}${args.posterPath}")
            .into(img_poster_detail)
    }
}
