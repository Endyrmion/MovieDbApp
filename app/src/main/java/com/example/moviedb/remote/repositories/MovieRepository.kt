package com.example.moviedb.remote.repositories

import com.example.moviedb.BuildConfig
import com.example.moviedb.remote.models.responses.*
import com.example.moviedb.remote.services.ApiService
import io.reactivex.Observable

class MovieRepository(private val apiService: ApiService) {

    fun getPopularMovies(page: Int): Observable<MoviesResponse> {
        return apiService.searchMovies(page, BuildConfig.API_LANG, BuildConfig.API_KEY)
    }

    fun getMoviesBySearch(page: Int, queryString: String): Observable<MoviesResponse>{
        return apiService.searchMovieByKeyWord(page, BuildConfig.API_LANG, BuildConfig.API_KEY, queryString, false)
    }

    fun getVideoById(movie_id: String): Observable<MovieVideoResponse> {
        return apiService.searchVideoById(movie_id, BuildConfig.API_LANG, BuildConfig.API_KEY)
    }

    fun getMovieDetailById(movie_id: String): Observable<MovieDetail> {
        return apiService.searchMovieDetailById(movie_id, BuildConfig.API_LANG, BuildConfig.API_KEY)
    }

    fun rateMovieById(movie_id: String, sessionId: String, rate: Rate): Observable<RatingResponse> {
        return apiService.rateMovie(movie_id, BuildConfig.API_KEY, sessionId, rate)
    }

    fun getAccountStatesById(movie_id: String, sessionId: String): Observable<AccountStates> {
        return apiService.getAccountStates(movie_id, BuildConfig.API_KEY, sessionId)
    }

    fun setMovieToWatchlist(sessionId: String, watchlistObject: WatchlistMovieObject): Observable<WatchlistAnswer> {
        return apiService.setToWatchlist(1, BuildConfig.API_KEY, sessionId, watchlistObject)
    }
}