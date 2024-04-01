package com.example.notepadmobile.utils

import com.example.notepadmobile.data.NoteMemento
import java.util.Stack

/**
 * Undo redo manager
 *
 * @constructor Create empty Undo redo manager
 */
class UndoRedoManager {
    private val history: Stack<NoteMemento> = Stack()
    private val redoHistory: Stack<NoteMemento> = Stack()

    /**
     * Undo
     *
     * @return
     */
    fun undo(): NoteMemento? {
        if (history.isEmpty()) {
            return null
        }
        val memento = history.pop()
        redoHistory.push(memento)
        return memento
    }

    /**
     * Redo
     *
     * @return
     */
    fun redo(): NoteMemento? {
        if (redoHistory.isEmpty()) {
            return null
        }
        val memento = redoHistory.pop()
        history.push(memento)
        return memento
    }

    /**
     * Add to history
     *
     * @param memento
     */
    fun addToHistory(memento: NoteMemento) {
        history.push(memento)
        redoHistory.clear() // Clear redo history on new changes
    }
}
