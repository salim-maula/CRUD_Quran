package com.example.bismilahjadi

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import com.pandamy.notepad.ConfirmDeleteNoteDialogFragment
import kotlinx.android.synthetic.main.activity_note_detail.*

class NoteDetailActivity : AppCompatActivity() {

    companion object{
        val EXTRA_NOTE = "notes"
        val EXTRA_NOTE_INDEX = "indexNotes"
        val REQUEST_EDIT_NOTE = 1

        val ACTION_SENDING_NEW_NOTE_VALUE = "com.pandamy.notepad.NoteDetailActivity.ACTION_SENDING_NEW_NOTE_VALUE"
        val ACTION_DELETE_NOTE = "com.pandamy.notepad.NoteDetailActivity.ACTION_DELETE_NOTE"
    }

    lateinit var note : Note
    var indexNote : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

            note = intent.getParcelableExtra<Note>(EXTRA_NOTE)!!
            indexNote = intent.getIntExtra(EXTRA_NOTE_INDEX, -1)

            titleDetail.setText(note.title)
            noteDetail.setText(note.txt)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_note_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_save -> {
                sendingNewNoteValue()
                true
            }
            R.id.action_delete -> {
                showDeleteNoteDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDeleteNoteDialog() {
        val dialogFragmentDeleteNote = ConfirmDeleteNoteDialogFragment(note.title)
        dialogFragmentDeleteNote.listener = object: ConfirmDeleteNoteDialogFragment.ConfirmDeleteNoteDialogListener{
            override fun onDialogPositiveClick() {
                deleteNote()
            }

            override fun onDialogNegativeClick() { }

        }
        dialogFragmentDeleteNote.show(supportFragmentManager, "deleteNote")
    }

    private fun sendingNewNoteValue() {
        note.title = titleDetail.text.toString()
        note.txt = noteDetail.text.toString()

        intent = Intent(ACTION_SENDING_NEW_NOTE_VALUE)
        intent.putExtra(EXTRA_NOTE, note as Parcelable)
        intent.putExtra(EXTRA_NOTE_INDEX,indexNote)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun deleteNote() {
        intent = Intent(ACTION_DELETE_NOTE)
        intent.putExtra(EXTRA_NOTE_INDEX, indexNote)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
}