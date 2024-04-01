package com.example.notepadmobile.data

import java.util.Date

/**
 * Note memento
 *
 * @property title
 * @property description
 * @property timestamp
 * @constructor Create empty Note memento
 */
data class NoteMemento(
    val title: String,
    val description: String,
    val timestamp: String,
)
