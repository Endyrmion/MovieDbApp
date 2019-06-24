package com.example.moviedb.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.R
import com.example.moviedb.remote.models.responses.Movie
import com.example.moviedb.remote.providers.MoviesRepositoryProvider
import com.example.moviedb.ui.infos.MoviesDetailsActivity
import com.example.moviedb.utils.extensions.InfiniteScrollListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment: Fragment() {

    private var page = 1
    private val layoutMng = LinearLayoutManager(this.context)
    private var moviesAdapter: MovieAdapter? = null
    private var movies = ArrayList<Movie>()

    companion object {
        //var queryStr = String
        const val MOVIE_ID_KEY =  "MOVIE_ID"
        fun newInstance(): MovieFragment {
            return MovieFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container:ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter = MovieAdapter(movies)
        moviesAdapter!!.setOnClickListener {
            val intent = Intent(context, MoviesDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID_KEY, it.id)
            startActivity(intent)
        }

        initRecyclerView()
        //getPopularMovies(page)
        getMoviesBySearch(page, "marvel")
        (activity as AppCompatActivity).setSupportActionBar(toolbarMovie)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.fragment_movie_menu, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "ex: Captain Marvel"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                    if(newText != "") {
                        movies.clear()
                        moviesAdapter?.notifyDataSetChanged()
                        Log.i("on Query Text Change : ", " value $newText")
                        getMoviesBySearch(page, newText)
                    }
                return false

            }
            override fun onQueryTextSubmit(query: String): Boolean {
                movies.clear()
                moviesAdapter?.notifyDataSetChanged()
                getMoviesBySearch(page, query)
                return false
            }

        })
        return
    }

    private fun initRecyclerView(){
        moviesList.apply {layoutManager = layoutMng}
        moviesList.adapter = moviesAdapter
        moviesList.addOnScrollListener(
            InfiniteScrollListener({getPopularMovies(page)},{nextPage()}, layoutMng))
    }

    @SuppressLint("CheckResult")
    private fun getPopularMovies(page: Int){
        val repository = MoviesRepositoryProvider.provideMovieRepository()
        repository.getPopularMovies(page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                movies.addAll(result.results)
                moviesAdapter!!.notifyDataSetChanged()
            }, { error ->
                error.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    private fun getMoviesBySearch(page: Int, query: String){
        val repository = MoviesRepositoryProvider.provideMovieRepository()
        repository.getMoviesBySearch(page, query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                movies.addAll(result.results)
                moviesAdapter!!.notifyDataSetChanged()
            }, { error ->
                error.printStackTrace()
            })
    }

    private fun nextPage() {
        this.page += 1
    }
}