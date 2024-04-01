import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notepadmobile.data.NoteItem
import com.example.notepadmobile.R

/**
 * Note adapter
 *
 * @property noteList
 * @property listener
 * @constructor Create empty Note adapter
 */
class NoteAdapter(private val noteList: List<NoteItem>, private val listener: OnNoteItemClickListener) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    /**
     * On note item click listener
     *
     * @constructor Create empty On note item click listener
     */
    interface OnNoteItemClickListener {
        /**
         * On note item click
         *
         * @param position
         */
        fun onNoteItemClick(position: Int)
    }

    /**
     * On create view holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    /**
     * On bind view holder
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentItem = noteList[position]
        holder.titleTextView.text = currentItem.title
        holder.descriptionTextView.text = currentItem.description
        holder.timestampTextView.text = currentItem.timestamp

        holder.itemView.setOnClickListener {
            listener.onNoteItemClick(position)
        }
    }

    /**
     * Get item count
     *
     */
    override fun getItemCount() = noteList.size

    /**
     * Filter
     *
     * @param newText
     */// Searching Method
    fun filter(newText: String?) {
        var filteredNoteList: List<NoteItem> = noteList;

        filteredNoteList = if (newText.isNullOrEmpty()) {
            noteList
        } else {
            noteList.filter { it.title.contains(newText, true) || it.description.contains(newText, true) }
        }
            notifyDataSetChanged()
    }

    /**
     * Note view holder
     *
     * @constructor
     *
     * @param itemView
     */
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleout)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionout)
        val timestampTextView: TextView = itemView.findViewById(R.id.timeoutout)
    }
}
