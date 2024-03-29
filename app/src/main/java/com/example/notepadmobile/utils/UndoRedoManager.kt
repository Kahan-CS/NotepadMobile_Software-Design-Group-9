package com.example.notepadmobile.utils

import com.example.notepadmobile.data.NoteMemento
import java.util.Stack

class UndoRedoManager {
    private val history: Stack<NoteMemento> = Stack()
    private val redoHistory: Stack<NoteMemento> = Stack()

    fun undo(): NoteMemento? {
        if (history.isEmpty()) {
            return null
        }
        val memento = history.pop()
        redoHistory.push(memento)
        return memento
    }

    fun redo(): NoteMemento? {
        if (redoHistory.isEmpty()) {
            return null
        }
        val memento = redoHistory.pop()
        history.push(memento)
        return memento
    }

    fun addToHistory(memento: NoteMemento) {
        history.push(memento)
        redoHistory.clear() // Clear redo history on new changes
    }
}
