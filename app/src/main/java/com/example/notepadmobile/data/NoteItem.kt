package com.example.notepadmobile.data

import com.example.notepadmobile.data.NoteMemento

data class NoteItem(
    var title: String,
    var description: String,
    var timestamp: String,
)
{
    // Create a memento of the current state
    fun createMemento(): NoteMemento {
        return NoteMemento(title, description, timestamp)
    }

    // Restore the state from a memento
    fun restoreFromMemento(memento: NoteMemento) {
        title = memento.title;
        description = memento.description;
        timestamp = memento.timestamp;
    }
}