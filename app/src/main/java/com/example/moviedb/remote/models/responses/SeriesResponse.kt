package com.example.moviedb.remote.models.responses


data class SeriesResponse(
    val page: Int,
    val results: ArrayList<Serie>
)

data class SerieVideoResponse(
    val id: String,
    val results: ArrayList<SerieVideo>
)