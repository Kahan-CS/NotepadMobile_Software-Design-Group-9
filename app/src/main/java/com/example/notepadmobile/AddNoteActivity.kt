package com.example.notepadmobile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Add note activity
 *
 * @constructor Create empty Add note activity
 */
class AddNoteActivity : AppCompatActivity() {
    lateinit var titleInput: EditText
    lateinit var descriptionText: EditText
    lateinit var saveNoteBtn: MaterialButton

    /**
     * On create
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        titleInput = findViewById(R.id.titleinput)
        descriptionText = findViewById(R.id.descriptioninput)
        saveNoteBtn = findViewById(R.id.savenotebtn)

        saveNoteBtn.setOnClickListener {
            saveNote()
        }
    }

    /**
     * Save note
     *
     */
    private fun saveNote() {
        val title = titleInput.text.toString()
        val description = descriptionText.text.toString()
        val timestamp = getCurrentTimestamp()

        val intent = Intent()
        intent.putExtra("title", title)
        intent.putExtra("description", description)
        intent.putExtra("timestamp", timestamp)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    /**
     * On back pressed
     *
     */
    override fun onBackPressed() {
        saveNote()
        super.onBackPressed()
    }

    /**
     * Get current timestamp
     *
     * @return
     */
    private fun getCurrentTimestamp(): String {
        val dateFormat = SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.getDefault())
        val currentTime = Calendar.getInstance().time
        return dateFormat.format(currentTime)
    }
}
