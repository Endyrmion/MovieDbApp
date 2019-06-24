package com.example.moviedb.remote.repositories

import com.example.moviedb.BuildConfig
import com.example.moviedb.remote.models.responses.SeasonDetails
import com.example.moviedb.remote.services.ApiSerieService
import io.reactivex.Observable

class SerieDetailsRepository(private val apiSerieService: ApiSerieService) {

    fun getTvShowDetail(tv_id: String): Observable<SeasonDetails> {
        return apiSerieService.getTvShowDetail(tv_id, BuildConfig.API_KEY, BuildConfig.API_LANG)
    }
}