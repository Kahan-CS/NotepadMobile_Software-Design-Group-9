package com.example.notepadmobile

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

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val titleInput = findViewById<EditText>(R.id.titleinput)
        val descriptionText = findViewById<EditText>(R.id.descriptioninput)
        val saveNoteBtn = findViewById<MaterialButton>(R.id.savenotebtn)

        saveNoteBtn.setOnClickListener {
            val title = titleInput.text.toString()
            val description = descriptionText.text.toString()
            val timestamp = getCurrentTimestamp()

            val intent = Intent()
            intent.putExtra("title", title)
            intent.putExtra("description", description)
            intent.putExtra("timestamp", timestamp)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun getCurrentTimestamp(): String {
        val dateFormat = SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.getDefault())
        val currentTime = Calendar.getInstance().time
        return dateFormat.format(currentTime)
    }
}