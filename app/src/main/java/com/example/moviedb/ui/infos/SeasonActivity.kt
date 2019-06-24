package com.example.moviedb.ui.infos

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.R
import com.example.moviedb.remote.models.responses.SeasonDetails
import com.example.moviedb.remote.providers.SerieDetailsRepositoryProvider
import com.example.moviedb.remote.repositories.SerieDetailsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SeasonActivity : AppCompatActivity() {

    var serie_object: SeasonDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.season_details)
        val serie_id = intent.getStringExtra(SeriesDetailsActivity.SERIE_ID_KEY)
        getTvShowDetail(serie_id)
    }

    @SuppressLint("checkResult")
    private fun getTvShowDetail(tv_id: String){
        val repository = SerieDetailsRepositoryProvider.provideSerieDetailRepository()
        repository.getTvShowDetail(tv_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                serie_object= result
                Log.i("Season Activity", "Object result : $result ")
            }, { error ->
                error.printStackTrace()
            })
    }

    @SuppressLint("checkthatf")
    private fun getTvDetails(tv_id: String){

    }
}