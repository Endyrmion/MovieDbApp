package com.example.moviedb.remote.services

import com.example.moviedb.BuildConfig
import com.example.moviedb.remote.models.responses.SeasonDetails
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiSerieService {


    @GET("tv/{tv_id}")
    fun getTvShowDetail(@Path("tv_id") movie_id: String,
                        @Query("api_key") api_key: String,
                        @Query("language") language: String): Observable<SeasonDetails>

                  companion object  {
        fun create(): ApiSerieService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.API_BASE_PATH)
                .build()
            return retrofit.create(ApiSerieService::class.java)
        }
    }
}