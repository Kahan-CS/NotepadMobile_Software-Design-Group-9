package com.example.notepadmobile.data

import com.example.notepadmobile.utils.UndoRedoManager

object NoteManager {
    private lateinit var currentFolder: Folder // Assume a current folder is selected
    private val undoRedoManager = UndoRedoManager()

    fun setCurrentFolder(folder: Folder) {
        currentFolder = folder
    }

    fun createNoteInFolder(name: String, content: String, author: String, labels: List<String>) {
        val note = Note(name, content, author, labels)
        currentFolder.addNoteToFolder(note)
        // Add note creation command to the undo/redo history
        undoRedoManager.addToHistory(note.createMemento())
    }

    fun undo() {
        val memento = undoRedoManager.undo()
        memento?.let { currentFolder.restoreNotesFromMemento(it) }
    }

    fun redo() {
        val memento = undoRedoManager.redo()
        memento?.let { currentFolder.restoreNotesFromMemento(it) }
    }

    // Implement other CRUD operations for notes and folders
}
