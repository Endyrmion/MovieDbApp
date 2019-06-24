package com.example.moviedb.remote.models.responses

data class Rating(
    val Source: String,
    val Value: String
)

data class ImdbMovie(
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Awards: String,
    val Ratings: ArrayList<Rating>,
    val Production: String
)