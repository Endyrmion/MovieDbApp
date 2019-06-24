package com.example.moviedb.remote.models.responses

data class Serie (
    val name: String,
    val first_air_date: String,
    val overview: String,
    val poster_path: String,
    val id: String
)

data class SerieVideo(
    val show_id: Int,
    val key: String
)