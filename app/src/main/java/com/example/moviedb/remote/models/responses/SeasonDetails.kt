package com.example.moviedb.remote.models.responses


data class created_by_object(
    val id: Int,
    val credit_id: String,
    val name: String,
    val gender: Int,
    val profile_path: String
)

data class genre(
    val id: Int,
    val name: String
)

data class last_episode_to_air_object(
    val air_date: String,
    val episode_number: Int,
    val id: String,
    val name: String,
    val overview: String,
    val production_code: String,
    val season_number: Int,
    val show_id: Int,
    val still_path: String,
    val vote_average: String,
    val vote_count: Int
)

data class network(
    val name: String,
    val id: Int,
    val logo_path:String,
    val origin_country: String
)

data class SeasonDetails(
    val created_by: ArrayList<created_by_object>,
    val genres: ArrayList<genre>,
    val first_air_date: String,
    val last_episode_to_air: last_episode_to_air_object,
    val name: String,
    val networks: ArrayList<network>,
    val number_of_episodes: Int,
    val number_of_seasons: Int
)


