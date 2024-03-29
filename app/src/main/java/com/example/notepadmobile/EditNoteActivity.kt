package com.example.notepadmobile

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

class EditNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        val titleInput = findViewById<EditText>(R.id.edit_title_input)
        val descriptionInput = findViewById<EditText>(R.id.edit_description_input)
        val saveChangesButton = findViewById<MaterialButton>(R.id.save_changes_button)
        val deleteButton = findViewById<MaterialButton>(R.id.delete_button)

        // Retrieve existing note details
        val title = intent.getStringExtra("title") ?: ""
        val description = intent.getStringExtra("description") ?: ""


        titleInput.setText(title)
        descriptionInput.setText(description)

        saveChangesButton.setOnClickListener {
            saveChanges()
        }

        deleteButton.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun saveChanges() {
        val editedTitle = findViewById<EditText>(R.id.edit_title_input).text.toString()
        val editedDescription = findViewById<EditText>(R.id.edit_description_input).text.toString()
        val timestamp = intent.getStringExtra("timestamp")?: ""

        val editIntent = Intent()
        editIntent.putExtra("editedTitle", editedTitle)
        editIntent.putExtra("editedDescription", editedDescription)
        editIntent.putExtra("editedTimestamp",timestamp)
        setResult(RESULT_OK, editIntent)
        finish()
    }

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Delete")
        builder.setMessage("Are you sure you want to delete this note?")
        builder.setPositiveButton("Delete") { dialogInterface: DialogInterface, _: Int ->
            deleteNote()
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun deleteNote() {
        val editedTitle = findViewById<EditText>(R.id.edit_title_input).text.toString()
        val editedDescription = findViewById<EditText>(R.id.edit_description_input).text.toString()
        val timestamp = intent.getStringExtra("timestamp")?: ""

        val deleteIntent = Intent()
        deleteIntent.putExtra("deleteNote", true)
        deleteIntent.putExtra("editedTitle", editedTitle) // Pass edited title back
        deleteIntent.putExtra("editedDescription", editedDescription) // Pass edited description back
        deleteIntent.putExtra("editedTimestamp", timestamp) // Pass edited timestamp back
        setResult(RESULT_OK, deleteIntent)
        finish()
    }


    private fun getCurrentTimestamp(): String {
        val dateFormat = SimpleDateFormat("MMM dd yyyy HH:mm", Locale.getDefault())
        val currentTime = Calendar.getInstance().time
        return dateFormat.format(currentTime)
    }
}

