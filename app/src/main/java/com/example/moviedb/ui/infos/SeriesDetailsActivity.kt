package com.example.moviedb.ui.infos

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.moviedb.BuildConfig
import com.example.moviedb.R
import com.example.moviedb.remote.models.responses.SerieVideoResponse
import com.example.moviedb.remote.providers.SeriesRepositoryProvider
import com.example.moviedb.ui.serie.SerieFragment
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.series_details.*


class SeriesDetailsActivity : AppCompatActivity() {

    private var serieVideo: SerieVideoResponse? = null

    companion object{
        val SERIE_ID_KEY = "SERIE_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.series_details)

        // Nav Bar Title
        val navBarTitle = intent.getStringExtra(SerieFragment.SERIES_TITLE_KEY)
        supportActionBar?.title = navBarTitle
        val serieId = intent.getStringExtra(SerieFragment.SERIE_ID_KEY)
        val seriePoster = intent.getStringExtra(SerieFragment.SERIE_POSTER)
        val serieDescription = intent.getStringExtra(SerieFragment.SERIE_DESCRIPTION)

        Picasso.get().load(BuildConfig.API_IMAGE_BASE_PATH+seriePoster).into(serieImageView)
        serieTitleTextView.text = navBarTitle
        descriptionSerie.text = serieDescription
        val btnClick = findViewById<Button>(R.id.trailerButtonSerie)
        val btnDetailSerie = findViewById<Button>(R.id.btnDetailSerie)

        //get the video information
        getSerieVideoById(serieId)

        // set on-click listener
        btnClick.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + serieVideo!!.results.first().key)))
        }
        // set on-click listener
        btnDetailSerie.setOnClickListener {
            val intent = Intent(baseContext, SeasonActivity::class.java)
            intent.putExtra(SERIE_ID_KEY, serieId)
            startActivity(intent)
        }
    }


    @SuppressLint("CheckResult")
    private fun getSerieVideoById(movieId: String){
        val repository = SeriesRepositoryProvider.provideSerieRepository()
        repository.getSerieVideoById(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                serieVideo = result
            }, { error ->
                error.printStackTrace()
            })
    }

}