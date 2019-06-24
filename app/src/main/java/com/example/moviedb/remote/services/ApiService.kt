package com.example.moviedb.remote.services

import com.example.moviedb.BuildConfig
import com.example.moviedb.remote.models.responses.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @GET("search/movie")
    fun searchMovieByKeyWord(@Query("page") page: Int,
                             @Query("language") lang: String,
                             @Query("api_key") key: String,
                             @Query("query") queryString: String,
                             @Query("include_adult") adult: Boolean): Observable<MoviesResponse>

    @GET("search/tv")
    fun searchShowByKeyWord(@Query("page")page: Int,
                            @Query("language") lang: String,
                            @Query("api_key") key: String,
                            @Query("query") queryString: String): Observable<MoviesResponse>


    @GET("movie/popular")
    fun searchMovies(@Query("page") page:Int,
                     @Query("language") lang:String,
                     @Query("api_key") key:String): Observable<MoviesResponse>

    @GET("tv/popular")
    fun searchSeries(@Query("page") page:Int,
                     @Query("language") lang:String,
                     @Query("api_key") key:String): Observable<SeriesResponse>

    @GET("movie/{movie_id}/videos")
    fun searchVideoById(@Path("movie_id") movie_id: String,
                        @Query("language") lang: String,
                        @Query("api_key") key: String): Observable<MovieVideoResponse>

    @GET("tv/{show_id}/videos")
    fun searchSerieVideoById(@Path("show_id") serie_id: String,
                             @Query("language") lang: String,
                             @Query("api_key") key: String): Observable<SerieVideoResponse>

    @GET("movie/{movie_id}")
    fun searchMovieDetailById(@Path("movie_id") movie_id: String,
                              @Query("language") lang: String,
                              @Query("api_key") key: String): Observable<MovieDetail>


    //Login
    @GET("authentication/token/new")
    fun createRequestToken(@Query("api_key") key: String): Observable<Login>

    @POST("authentication/session/new")
    fun createSession(@Query("api_key") key:String,
                      @Body sessionCreationData: SessionCreationData ) : Observable<SessionResponse>


    // USER ACTIVITIES

    @POST("movie/{movie_id}/rating")
    fun rateMovie(@Path("movie_id") movie_id: String,
                  @Query("api_key") key: String,
                  @Query("session_id") session_id: String,
                  @Body rate: Rate) : Observable<RatingResponse>



    @GET("movie/{movie_id}/account_states")
    fun getAccountStates(@Path("movie_id") movie_id: String,
                         @Query("api_key") key: String,
                         @Query("session_id") session_id: String) : Observable<AccountStates>

    @POST("account/{account_id}/watchlist")
    fun setToWatchlist(@Path("account_id") account_id: Int,
                       @Query("api_key") key: String,
                       @Query("session_id") session_id: String,
                       @Body watchlistObject: WatchlistMovieObject) : Observable<WatchlistAnswer>

    companion object  {

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.API_BASE_PATH)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

}