package com.example.moviedb.ui.serie

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.BuildConfig
import com.example.moviedb.R
import com.example.moviedb.remote.models.responses.Serie
import com.example.moviedb.utils.extensions.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.serie_item.view.*

class SerieAdapter (private val series: ArrayList<Serie>) : RecyclerView.Adapter<SerieAdapter.SerieHolder>()  {

    private var itemClickListener: ((Serie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieHolder {
        val inflatedView = parent.inflate(R.layout.serie_item, false)
        return SerieHolder(inflatedView)
    }

    override fun getItemCount(): Int = series.size


    override fun onBindViewHolder(holder: SerieHolder, position: Int) {
        val itemPhoto = series[position]
        holder.bindSerie(itemPhoto, itemClickListener!!)
    }

    fun setOnClickListener(listener: (item: Serie) -> Unit){
        itemClickListener = listener
    }

    class SerieHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View = v
        private var serie: Serie? = null

        fun bindSerie(serie: Serie, clickListener: (Serie) -> Unit) {
            this.serie = serie
            Picasso.get().load(BuildConfig.API_IMAGE_BASE_PATH+serie.poster_path).into(view.seriePoster)
            view.first_air_date.text = serie.first_air_date
            view.name.text = serie.name
            view.overview.text= serie.overview
            view.setOnClickListener { clickListener.invoke(serie) }
        }
    }
}