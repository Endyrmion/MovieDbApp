package com.example.moviedb.remote.models.responses

data class MoviesResponse(
    val page: Int,
    val results: ArrayList<Movie>
)

data class MovieVideoResponse(
    val id: String,
    val results: ArrayList<MovieVideo>
)

data class MovieDetailResponse(
    val id: String,
    val results: ArrayList<MovieDetail>
)