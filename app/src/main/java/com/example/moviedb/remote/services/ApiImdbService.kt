package com.example.moviedb.remote.services

import com.example.moviedb.BuildConfig
import com.example.moviedb.remote.models.responses.ImdbMovie
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiImdbService {
    @GET("/")
    fun searchImdbMovie(@Query("i") i: String,
                        @Query("apikey") apikey: String): Observable<ImdbMovie>

    companion object  {
        fun create(): ApiImdbService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.API_BASE_PATH_IMDB)
                .build()
            return retrofit.create(ApiImdbService::class.java)
        }
    }
}