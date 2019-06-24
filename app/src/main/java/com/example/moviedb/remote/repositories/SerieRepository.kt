package com.example.moviedb.remote.repositories

import com.example.moviedb.BuildConfig
import com.example.moviedb.remote.models.responses.SeasonDetails
import com.example.moviedb.remote.models.responses.SerieVideoResponse
import com.example.moviedb.remote.models.responses.SeriesResponse
import com.example.moviedb.remote.services.ApiService
import io.reactivex.Observable


class SerieRepository(private val apiService: ApiService) {

    fun getPopularSeries(page: Int): Observable<SeriesResponse> {
        return apiService.searchSeries(page, BuildConfig.API_LANG, BuildConfig.API_KEY)
    }

    fun getSerieVideoById(show_id: String): Observable<SerieVideoResponse>{
        return apiService.searchSerieVideoById(show_id, BuildConfig.API_LANG, BuildConfig.API_KEY)
    }
}