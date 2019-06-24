package com.example.moviedb.remote.providers

import com.example.moviedb.remote.repositories.MovieRepository
import com.example.moviedb.remote.services.ApiService

object MoviesRepositoryProvider{

    fun provideMovieRepository(): MovieRepository {
        return MovieRepository(ApiService.create())
    }
}