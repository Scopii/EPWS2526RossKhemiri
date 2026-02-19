package com.example.datadetective.data

data class UserProfile(
    val name: String,
    var titel: String? = null,
    val xp: Int = 0,
    val level: Int = 1
)