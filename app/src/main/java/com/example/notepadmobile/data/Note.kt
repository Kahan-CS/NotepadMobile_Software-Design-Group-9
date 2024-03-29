package com.example.notepadmobile.data

import java.util.Date

class Note(
    var name: String,
    var content: String,
    var author: String,
    var labels: List<String>
) {
    private var creationDate: Date = Date()
    private var lastUpdatedDate: Date = Date()

    // Create a memento of the current state
    fun createMemento(): NoteMemento {
        return NoteMemento(name, content, author, labels, creationDate, lastUpdatedDate)
    }

    // Restore the state from a memento
    fun restoreFromMemento(memento: NoteMemento) {
        name = memento.name
        content = memento.content
        author = memento.author
        labels = memento.labels
        creationDate = memento.creationDate
        lastUpdatedDate = memento.lastUpdatedDate
    }
}
