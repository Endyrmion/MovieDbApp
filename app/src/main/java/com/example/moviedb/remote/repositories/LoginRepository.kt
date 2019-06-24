package com.example.moviedb.remote.repositories

import com.example.moviedb.BuildConfig
import com.example.moviedb.remote.models.responses.Login
import com.example.moviedb.remote.models.responses.SessionCreationData
import com.example.moviedb.remote.models.responses.SessionResponse
import com.example.moviedb.remote.services.ApiService
import io.reactivex.Observable

class LoginRepository(private val apiService: ApiService) {

    fun getRequestToken(): Observable<Login> {
        return apiService.createRequestToken(BuildConfig.API_KEY)
    }

    fun createSession(sessionCreationData: SessionCreationData): Observable<SessionResponse> {
        return apiService.createSession(BuildConfig.API_KEY, sessionCreationData)
    }
}