package com.example.moviedb.remote.models.responses

data class Movie(
    val title: String,
    val release_date: String,
    val overview: String,
    val poster_path: String,
    val id: String
)

data class MovieVideo(
    val movie_id: Int,
    val key: String
)

data class GenreObject(
    val id: Int,
    val name: String
)

data class MovieDetail(
    val backdrop_path: String,
    val original_title: String,
    val overview: String,
    val budget: String,
    val homepage: String,
    val imdb_id: String,
    val genres: ArrayList<GenreObject>,
    val original_language: String,
    val popularity: String,
    val release_date: String,
    val revenue: String,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val vote_average: String
)