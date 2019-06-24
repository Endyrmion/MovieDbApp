package com.example.moviedb.remote.models.responses


//      AccountStates
//
//    -User Movie rating
//    -If it belongs to your watchlist
//    -If it belongs to your favourite list

data class AccountStates(
    val id: Int,
    val favorite: Boolean,
    val watchlist: Boolean
)

data class AccountId(
    val account_id: Int
)

data class WatchlistMovieObject(
    val media_type: String,
    val media_id: Int,
    val watchlist: Boolean
)

data class WatchlistAnswer(
    val status_code: Int,
    val status_message: String
)
