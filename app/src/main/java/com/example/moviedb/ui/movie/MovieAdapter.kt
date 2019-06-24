package com.example.moviedb.ui.movie

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.BuildConfig
import com.example.moviedb.R
import com.example.moviedb.remote.models.responses.Movie
import com.example.moviedb.utils.extensions.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter (private val movies: ArrayList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    private var itemClickListener: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflatedView = parent.inflate(R.layout.movie_item, false)
        return MovieHolder(inflatedView)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val itemPhoto = movies[position]
        holder.bindMovie(itemPhoto, itemClickListener!!)
    }

    fun setOnClickListener(listener: (item: Movie) -> Unit) {
        itemClickListener = listener
    }

    class MovieHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View = v
        private var movie: Movie? = null

        fun bindMovie(movie: Movie, clickListener: (Movie) -> Unit) {
            this.movie = movie
            Picasso.get().load(BuildConfig.API_IMAGE_BASE_PATH + movie.poster_path).into(view.moviePoster)
            view.title.text = movie.title
            view.overview.text = movie.overview
            view.setOnClickListener {
                clickListener.invoke(movie)
            }
        }
    }
}