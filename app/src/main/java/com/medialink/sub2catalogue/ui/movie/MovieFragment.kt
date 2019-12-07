package com.medialink.sub2catalogue.ui.movie

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.medialink.sub2catalogue.R
import com.medialink.sub2catalogue.models.movie.Movie
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.layout_error.*

class MovieFragment : Fragment(), MovieAdapter.ItemClickListener {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter

    companion object {
        const val TAG = "debug"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_movie, container, false)
        activity?.let {
            movieViewModel = ViewModelProviders.of(it).get(MovieViewModel::class.java)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUi()
    }

    private fun setupViewModel() {
        movieViewModel.movies.observe(this, renderMovies)
        movieViewModel.isViewLoading.observe(this, isViewLoadingObserver)
        movieViewModel.onMessageError.observe(this, onMessageErrorObserver)
        movieViewModel.isEmptyList.observe(this, emptyListObserver)
    }

    private fun setupUi() {
        adapter = MovieAdapter(
            movieViewModel.movies.value ?: emptyList(),
            this
        )

        rv_movie.setHasFixedSize(true)
        val i = resources.configuration.orientation
        if (i == Configuration.ORIENTATION_PORTRAIT) {
            rv_movie.layoutManager = LinearLayoutManager(context)
            rv_movie.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        } else {
            rv_movie.layoutManager = GridLayoutManager(context, 2)
            rv_movie.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            rv_movie.addItemDecoration(DividerItemDecoration(context, RecyclerView.HORIZONTAL))
        }

        rv_movie.adapter = adapter


    }

    private val renderMovies = Observer<List<Movie>> {
        layout_error.visibility = View.GONE
        layout_empty.visibility = View.GONE
        adapter.update(it)
        Log.d(TAG, "data updated $it")
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        progress_movie.visibility = visibility

        Log.d(TAG, "isViewLoading $it")
    }

    private val onMessageErrorObserver = Observer<Any> {
        layout_empty.visibility = View.GONE
        layout_error.visibility = View.VISIBLE
        tv_error.text = "Error $it"

        Log.d(TAG, "emptyListObserver $it")
    }

    private val emptyListObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        layout_empty.visibility = visibility
        Log.d(TAG, "emptyListObserver $it")
    }

    override fun onItemClicked(movie: Movie) {
        val toMovieDetailFragment = MovieFragmentDirections
            .actionNavigationMovieToMovieDetailFragment(movie)
        findNavController().navigate(toMovieDetailFragment)
    }
}