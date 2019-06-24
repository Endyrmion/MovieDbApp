package com.example.moviedb.remote.providers

import com.example.moviedb.remote.repositories.SerieRepository
import com.example.moviedb.remote.services.ApiService

object SeriesRepositoryProvider{

    fun provideSerieRepository(): SerieRepository {
        return SerieRepository(ApiService.create())
    }
}