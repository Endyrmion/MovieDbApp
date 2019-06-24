package com.example.moviedb.utils.extensions

class UserVote{

    var id: String
    var vote: Int = 0

    constructor(id: String, vote: Int) {
        this.id = id
        this.vote= vote
    }
}