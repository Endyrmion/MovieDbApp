package com.example.moviedb.remote.models.responses

data class ImdbMovieResponse(
    val id: String,
    val results: ArrayList<ImdbMovie>
)
