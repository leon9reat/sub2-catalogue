package com.medialink.sub2catalogue.ui.tv_show


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.medialink.sub2catalogue.Consts
import com.medialink.sub2catalogue.R
import com.medialink.sub2catalogue.models.tv_show.TvShow
import kotlinx.android.synthetic.main.fragment_tv_show_detail.*
import kotlinx.android.synthetic.main.layout_detail_description.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_detail, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> Log.d("debug", "home clicked")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: TvShow = TvShowDetailFragmentArgs.fromBundle(arguments as Bundle).tvShow

        tv_title_tv_show.text = args.name
        tv_airing_date.text = args.firstAirDate
        val i = args.voteAverage?.times(10)?.toInt() ?: 0
        progress_vote_tv_show.progress = i
        tv_vote_tv_show.text = "${i}%"
        tv_overview.text = args.overview
        Glide.with(this)
            .load("${Consts.TMDB_PHOTO_URL2}${args.posterPath}")
            .placeholder(R.drawable.ic_cloud_download_black_24dp)
            .error(R.drawable.ic_error_outline_black_24dp)
            .fallback(R.drawable.ic_not_interested_black_24dp)
            .into(img_poster_tv_show)
    }
}
