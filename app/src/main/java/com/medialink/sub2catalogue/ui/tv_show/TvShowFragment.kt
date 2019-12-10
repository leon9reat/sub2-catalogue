package com.medialink.sub2catalogue.ui.tv_show

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.medialink.sub2catalogue.R
import com.medialink.sub2catalogue.models.tv_show.TvShow
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.android.synthetic.main.layout_error.*
import java.util.*

class TvShowFragment : Fragment(), TvShowAdapter.ItemClickListener {

    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var tvShowAdapter: TvShowAdapter

    companion object {
        const val TAG = "debug"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            tvShowViewModel =
                ViewModelProviders.of(it).get(TvShowViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tv_show, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_dashboard)
        tvShowViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupUi()

        if (!Locale.getDefault().language.equals(tvShowViewModel.lokal)) {
            tvShowViewModel.lokal = Locale.getDefault().language
            tvShowViewModel.loadTvShow(1)
        }
        Toast.makeText(context, "TODO: Refresh", Toast.LENGTH_SHORT).show()
    }

    private fun setupViewModel() {
        tvShowViewModel.tvShows.observe(this, renderTvShows)
        tvShowViewModel.isViewLoading.observe(this, isViewLoadingObserve)
        tvShowViewModel.isEmptyList.observe(this, isEmptyListObserve)
        tvShowViewModel.onMessageError.observe(this, onMessageErrorObserve)
    }

    private fun setupUi() {
        rv_tv_show.setHasFixedSize(true)
        val i = resources.configuration.orientation
        if (i == Configuration.ORIENTATION_PORTRAIT) {
            rv_tv_show.layoutManager = LinearLayoutManager(context)
            rv_tv_show.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        } else {
            rv_tv_show.layoutManager = GridLayoutManager(context, 2)
            rv_tv_show.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }

        tvShowAdapter = TvShowAdapter(
            tvShowViewModel.tvShows.value ?: emptyList(),
            this
        )
        rv_tv_show.adapter = tvShowAdapter
    }

    private val renderTvShows = Observer<List<TvShow>> {
        layout_error.visibility = View.GONE
        layout_empty.visibility = View.GONE
        tvShowAdapter.update(it)
    }

    private val isViewLoadingObserve = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        progress_tv_show.visibility = visibility
    }

    private val isEmptyListObserve = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        layout_empty.visibility = visibility
    }

    private val onMessageErrorObserve = Observer<Any> {
        layout_empty.visibility = View.GONE
        layout_error.visibility = View.VISIBLE
        tv_error.text = "Error $it"
    }

    override fun onItemClick(tvShow: TvShow) {
        val toTvShowDetailFragment = TvShowFragmentDirections
            .actionNavigationTvShowToTvShowDetailFragment(tvShow)
        findNavController().navigate(toTvShowDetailFragment)
    }
}