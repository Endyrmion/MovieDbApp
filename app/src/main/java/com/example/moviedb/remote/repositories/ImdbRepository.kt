package com.example.moviedb.remote.repositories

import com.example.moviedb.BuildConfig
import com.example.moviedb.remote.models.responses.ImdbMovie
import com.example.moviedb.remote.services.ApiImdbService
import io.reactivex.Observable


class ImdbRepository(private val apiImdbService: ApiImdbService) {
    fun getImdbMovie(imdbId: String): Observable<ImdbMovie> {
            return apiImdbService.searchImdbMovie(imdbId, BuildConfig.API_KEY_IMDB)
    }
}
