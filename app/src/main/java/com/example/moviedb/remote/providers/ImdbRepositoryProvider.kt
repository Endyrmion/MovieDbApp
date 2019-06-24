package com.example.moviedb.remote.providers

import com.example.moviedb.remote.repositories.ImdbRepository
import com.example.moviedb.remote.services.ApiImdbService


object ImdbRepositoryProvider{

    fun provideImdbRepository(): ImdbRepository {
        return ImdbRepository(ApiImdbService.create())
    }
}