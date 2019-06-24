package com.example.moviedb.remote.models.responses

data class Rate(
    var value: String
)

data class RatingResponse(
    val status_code: Int,
    val status_message: String
)