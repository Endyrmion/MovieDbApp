package com.example.moviedb.remote.providers

import com.example.moviedb.remote.repositories.SerieDetailsRepository
import com.example.moviedb.remote.services.ApiSerieService

object SerieDetailsRepositoryProvider {
    fun provideSerieDetailRepository(): SerieDetailsRepository {
        return SerieDetailsRepository(ApiSerieService.create())
    }
}