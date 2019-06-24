package com.example.moviedb.remote.providers

import com.example.moviedb.remote.repositories.LoginRepository
import com.example.moviedb.remote.services.ApiService

object LoginRepositoryProvider {

    fun provideLoginRepository(): LoginRepository {
        return LoginRepository(ApiService.create())
    }
}