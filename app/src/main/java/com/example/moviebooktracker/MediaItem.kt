package com.example.moviebooktracker

data class MediaItem(
    var title: String,
    var genre: String,
    var review: String,
    var rating: Int,
    var isWatchedOrRead: Boolean,
    var type: String // "Film" lub "Książka"
)
