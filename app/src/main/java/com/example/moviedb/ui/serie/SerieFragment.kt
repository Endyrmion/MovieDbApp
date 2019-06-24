package com.example.moviedb.ui.serie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.annotation.SuppressLint
import android.content.Intent
import com.example.moviedb.remote.providers.SeriesRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_serie.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.R
import com.example.moviedb.remote.models.responses.Serie
import com.example.moviedb.ui.infos.SeriesDetailsActivity
import com.example.moviedb.utils.extensions.InfiniteScrollListener

class SerieFragment: Fragment() {

    private var page = 1
    private var seriesAdapter: SerieAdapter? = null
    private val layoutMng = LinearLayoutManager(this.context)
    private var series = ArrayList<Serie>()

    companion object {
        fun newInstance() = SerieFragment()
        val SERIES_TITLE_KEY = "MOVIES_SERIES_TITLE"
        val SERIE_ID_KEY = "SERIE_ID"
        val SERIE_DESCRIPTION = "SERIE_DESCRPTION"
        val SERIE_POSTER = "SERIE_POSTER"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_serie, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seriesAdapter = SerieAdapter(series)
        seriesAdapter!!.setOnClickListener {
            val intent = Intent(context, SeriesDetailsActivity::class.java)
            intent.putExtra(SERIES_TITLE_KEY, it.name)
            intent.putExtra(SERIE_ID_KEY, it.id)
            intent.putExtra(SERIE_DESCRIPTION, it.overview)
            intent.putExtra(SERIE_POSTER, it.poster_path)
            startActivity(intent)
        }
        initRecyclerView()
        getPopularSeries(page)
    }

    private fun initRecyclerView() {
        seriesList.apply { layoutManager = layoutMng }
        seriesList.adapter = seriesAdapter
        seriesList.addOnScrollListener(
            InfiniteScrollListener({ getPopularSeries(page) }, { nextPage() }, layoutMng)
        )
    }

    @SuppressLint("checkResult")
    private fun getPopularSeries(page: Int) {
        val repository = SeriesRepositoryProvider.provideSerieRepository()
        repository.getPopularSeries(page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                series.addAll(result.results)
                seriesAdapter!!.notifyDataSetChanged()
            }, { error ->
                error.printStackTrace()
            })
    }

    private fun nextPage() {
        this.page += 1
    }

}