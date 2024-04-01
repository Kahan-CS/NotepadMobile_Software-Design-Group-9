package com.example.notepadmobile

import NoteAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepadmobile.data.NoteItem
import com.example.notepadmobile.utils.UndoRedoManager
import com.google.android.material.button.MaterialButton

/**
 * Main activity
 *
 * @constructor Create empty Main activity
 */
class MainActivity : AppCompatActivity(), NoteAdapter.OnNoteItemClickListener {

    private val noteList = mutableListOf<NoteItem>()
    private lateinit var adapter: NoteAdapter
    private val careTaker = UndoRedoManager();

    /**
     * On create
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NoteAdapter(noteList, this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val undoBtn = findViewById<MaterialButton>(R.id.undobtn)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addNoteButton = findViewById<MaterialButton>(R.id.addnewnotebutton)
        addNoteButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }

        undoBtn.setOnClickListener {
            undoBtnClick()
        }
    }

    /**
     * Undo btn click
     *
     */
    fun undoBtnClick()
    {
        val noteMemento = careTaker.undo()
        val note = NoteItem("", "", "")
        if (noteMemento != null) {
            note.restoreFromMemento(noteMemento)
        }

        noteList.add(note)
        adapter.notifyDataSetChanged()
    }

    /**
     * On activity result
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ADD_NOTE_REQUEST -> handleAddNoteResult(resultCode, data)
            EDIT_NOTE_REQUEST -> handleEditNoteResult(resultCode, data)
        }
    }

    /**
     * Handle add note result
     *
     * @param resultCode
     * @param data
     */
    private fun handleAddNoteResult(resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val title = data?.getStringExtra("title") ?: ""
            val description = data?.getStringExtra("description") ?: ""
            val timestamp = data?.getStringExtra("timestamp") ?: ""
            val noteItem = NoteItem(title, description, timestamp)
            noteList.add(noteItem)
            adapter.notifyDataSetChanged()
        }
    }

    /**
     * Handle edit note result
     *
     * @param resultCode
     * @param data
     */
    private fun handleEditNoteResult(resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val deleteNote = data?.getBooleanExtra("deleteNote", false) ?: false
            if (deleteNote) {
                handleDeletedNoteResult(data)
            } else {
                handleEditedNoteResult(data)
            }
            adapter.notifyDataSetChanged()
        }
    }

    /**
     * Handle deleted note result
     *
     * @param data
     */
    private fun handleDeletedNoteResult(data: Intent?) {
        val editedTitle = data?.getStringExtra("editedTitle") ?: ""
        val editedDescription = data?.getStringExtra("editedDescription") ?: ""
        val editedTimestamp = data?.getStringExtra("editedTimestamp") ?: ""

        // Find and remove the edited note from the list
        val iterator = noteList.iterator()
        while (iterator.hasNext()) {
            val note = iterator.next()
            if (
                note.timestamp == editedTimestamp
            ) {
                careTaker.addToHistory(note.createMemento())
                iterator.remove()
                break
            }
        }
    }

    /**
     * Handle edited note result
     *
     * @param data
     */
    private fun handleEditedNoteResult(data: Intent?) {
        val editedTitle = data?.getStringExtra("editedTitle") ?: ""
        val editedDescription = data?.getStringExtra("editedDescription") ?: ""
        val editedTimestamp = data?.getStringExtra("editedTimestamp") ?: ""


        println(editedDescription)
        println(editedTitle)
        println(editedTimestamp)

        // Find the edited note and update its details
        for (i in noteList.indices) {
            val note = noteList[i]
            if (note.timestamp == editedTimestamp
            ) {
                noteList[i] = NoteItem(editedTitle, editedDescription, editedTimestamp)
                break
            }
        }
    }

    /**
     * On note item click
     *
     * @param position
     */
    override fun onNoteItemClick(position: Int) {
        val selectedNote = noteList[position]
        val editIntent = Intent(this, EditNoteActivity::class.java)
        editIntent.putExtra("title", selectedNote.title)
        editIntent.putExtra("description", selectedNote.description)
        editIntent.putExtra("timestamp", selectedNote.timestamp)
        startActivityForResult(editIntent, EDIT_NOTE_REQUEST)
    }

    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2
    }
}


