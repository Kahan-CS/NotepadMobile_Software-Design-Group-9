package com.example.notepadmobile.data

import java.util.Date

data class NoteMemento(
    val name: String,
    val content: String,
    val author: String,
    val labels: List<String>,
    val creationDate: Date,
    val lastUpdatedDate: Date
)
