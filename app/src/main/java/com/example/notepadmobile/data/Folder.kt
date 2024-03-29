package com.example.notepadmobile.data

import java.io.File
import java.util.Stack

class Folder(private val folderName: String) {
    private val folder: File = File(folderName)
    private val noteHistory: Stack<NoteMemento> = Stack()

    init {
        if (!folder.exists()) {
            folder.mkdirs()
        }
    }

    fun addNoteToFolder(note: Note) {
        // Save the note to the folder
        val noteFile = File(folder, "${note.name}.txt")
        noteFile.writeText(note.content)
        // Add note memento to the history
        noteHistory.push(note.createMemento())
    }

    fun searchNotes(query: String): List<Note> {
        // Search for notes in the folder based on the query
        val matchingNotes = mutableListOf<Note>()
        folder.listFiles()?.forEach { file ->
            val noteName = file.nameWithoutExtension
            val noteContent = file.readText()
            if (noteName.contains(query, ignoreCase = true) || noteContent.contains(query, ignoreCase = true)) {
                matchingNotes.add(Note(noteName, noteContent, "", listOf()))
            }
        }
        return matchingNotes
    }

    fun restoreNotesFromMemento(memento: NoteMemento) {
        // Iterate through the notes in the folder and restore them using the provided memento
        folder.listFiles()?.forEach { file ->
            val noteName = file.nameWithoutExtension
            // Restore note only if its name matches with the name in the memento
            if (noteName == memento.name) {
                // Read the content from the memento and update the file content
                val restoredContent = memento.content
                file.writeText(restoredContent)
            }
        }
    }

}
