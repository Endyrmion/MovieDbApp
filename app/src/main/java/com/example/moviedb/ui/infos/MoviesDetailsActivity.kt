package com.example.moviedb.ui.infos

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moviedb.BuildConfig
import com.example.moviedb.R
import com.example.moviedb.remote.models.responses.*
import com.example.moviedb.remote.providers.ImdbRepositoryProvider
import com.example.moviedb.remote.providers.MoviesRepositoryProvider
import com.example.moviedb.ui.home.LoginActivity
import com.example.moviedb.ui.home.MainActivity
import com.example.moviedb.ui.movie.MovieFragment
import com.example.moviedb.utils.extensions.SharedPreference
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movies_details.*


class MoviesDetailsActivity : AppCompatActivity() {

    private var movieVideo: MovieVideoResponse? = null
    private var movieDetail: MovieDetail? = null
    private var imdbMovie: ImdbMovie? = null


    companion object {
        const val MOVIE_ID_KEY_TO_VIDEO =  "MOVIE_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_details)
        val sharedPreference: SharedPreference = SharedPreference(this)
        val session_id = sharedPreference.getValueString("session_id")

        //toolbar
        setSupportActionBar(toolbarMovieDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movieId = intent.getStringExtra(MovieFragment.MOVIE_ID_KEY)
        val btnClick = findViewById<Button>(R.id.trailerButton)
        val btnImdbClick = findViewById<Button>(R.id.homepageButton)
        val btnHomepageClick = findViewById<Button>(R.id.imdbButton)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)

        //get the video information
        getVideoById(movieId)
        getMovieDetailById(movieId)
        getAccountStatesById(movieId, session_id!!)

        if (ratingBar != null) {
            val button = findViewById<Button>(R.id.submitButton)
            button?.setOnClickListener {
                val rating = ratingBar.rating * 2
                val value = rating.toString()
                rateMovieById(movieId, session_id, value)
            }
        }

        checkBox_watchlist.setOnClickListener {
            if (checkBox_watchlist.isChecked) {
                setMovieToWatchlist(movieId, session_id, true) } else {
                setMovieToWatchlist(movieId, session_id, false)
            }
        }

        // set on-click listener
        btnClick.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + movieVideo!!.results.first().key)))
        }
        btnImdbClick.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/showtimes/title/"+ movieDetail!!.imdb_id)))
        }
        btnHomepageClick.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(movieDetail!!.homepage)))
        }
    }

    //MENU BAR

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movie_detail_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }


    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            val intent = Intent(baseContext, MainActivity::class.java)

            startActivity(intent)
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun setMovieState(state: AccountStates){
        if (state.watchlist == true) {
            checkBox_watchlist.isChecked = true
        }
    }

    private fun setupView(movieDetail: MovieDetail){
        Picasso.get().load(BuildConfig.API_IMAGE_BASE_PATH+movieDetail.backdrop_path).into(imageView)

        if(movieDetail.overview == ""){
            description.text = "Pas encore de synopsis pour ce film."
        } else {
            description.text = movieDetail.overview
        }
        movieTitleTextView.text = movieDetail.original_title
        tagline.text = movieDetail.tagline
        status.text = movieDetail.status
        vote_average.text = movieDetail.vote_average
        supportActionBar?.title = movieDetail.original_title
    }

    @SuppressLint("SetTextI18n")
    private fun endSetupView(imdbMovie: ImdbMovie){
        genres.text = imdbMovie.Genre
        Director.text = "Directed by : ${imdbMovie.Director}"
        Released.text = imdbMovie.Released
        Production.text = "Producted by : ${imdbMovie.Production}"
        if (imdbMovie.Awards != "N/A") {
            Awards.text = imdbMovie.Awards
        } else {
            Awards.text = "Pas encore de récompenses pour ce film."
        }
    }

    @SuppressLint("CheckResult")
    private fun getImdbMovie(imdbId: String){
        val repository = ImdbRepositoryProvider.provideImdbRepository()
        repository.getImdbMovie(imdbId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                Log.i("getImdbMovie","result content: ${result}")
                imdbMovie = result
                endSetupView(result)
            }, { error ->
                error.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    private fun getMovieDetailById(movieId: String){
        val repository = MoviesRepositoryProvider.provideMovieRepository()
        repository.getMovieDetailById(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                movieDetail = result
                Log.i("GET_MOVIE_DETAILS"," result.imdb : ${result.imdb_id}")
                getImdbMovie(result.imdb_id)
                setupView(result)
            }, { error ->
                error.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    private fun getVideoById(movieId: String){
        val repository = MoviesRepositoryProvider.provideMovieRepository()
        repository.getVideoById(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                movieVideo = result
            }, { error ->
                error.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    private fun rateMovieById(movieId: String, sessionId: String, value: String){
        val repository = MoviesRepositoryProvider.provideMovieRepository()
        repository.rateMovieById(movieId, sessionId, Rate(value))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                Log.i("VOTE REQUEST ", " RESULT = $result ")
            }, { error ->
                error.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    private fun getAccountStatesById(movieId: String, sessionId: String){
        val repository = MoviesRepositoryProvider.provideMovieRepository()
        repository.getAccountStatesById(movieId, sessionId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                Log.i("GET ACCOUNT STATES"," result : $result")
                setMovieState(result)
            }, { error ->
                error.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    private fun setMovieToWatchlist(movieId: String, sessionId: String, isInList: Boolean){
       val repository = MoviesRepositoryProvider.provideMovieRepository()
        repository.setMovieToWatchlist(sessionId, WatchlistMovieObject("movie", movieId.toInt(), isInList))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                Log.i("VOTE REQUEST ", " RESULT = $result ")

               }, { error ->
                error.printStackTrace()
            })
    }

}