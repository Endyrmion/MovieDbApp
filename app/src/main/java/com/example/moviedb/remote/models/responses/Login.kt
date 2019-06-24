package com.example.moviedb.remote.models.responses


data class Login(
    val success: Boolean,
    val request_token: String,
    val expires_at: String
)

data class SessionResponse(
    val success: Boolean,
    val session_id: String
)

data class SessionCreationData(
    val request_token: String
)