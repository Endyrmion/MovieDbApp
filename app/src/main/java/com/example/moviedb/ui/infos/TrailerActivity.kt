package com.example.moviedb.ui.infos

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.moviedb.R
import com.example.moviedb.remote.models.responses.MovieVideoResponse
import com.example.moviedb.remote.providers.MoviesRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.content.Intent
import android.net.Uri


class TrailerActivity : AppCompatActivity() {

    private var movieVideo: MovieVideoResponse? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trailer_movie)

        val movieId = intent.getStringExtra(MoviesDetailsActivity.MOVIE_ID_KEY_TO_VIDEO)
        Log.i("TRAILER ACTIVITY","the movie trailer is : $movieId")
        getVideoById(movieId)
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=cxLG2wtE7TM")))
    }



    @SuppressLint("CheckResult")
    private fun getVideoById(movieId: String){
        val repository = MoviesRepositoryProvider.provideMovieRepository()
        repository.getVideoById(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                movieVideo = result
                Log.i("Trailer Activity", "Response :$movieVideo with the movie id : $movieId")
                Log.i("Trailer Activity ","înformation:")
            }, { error ->
                error.printStackTrace()
            })
    }

    @SuppressLint("CheckTheResult")
    private fun getFriday(movieId: String){
        val repository = MoviesRepositoryProvider
    }
}