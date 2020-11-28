package com.example.bismilahjadi

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bismillahjadi.utils.deleteNoteStorage
import com.example.bismillahjadi.utils.loadNotesStorage
import com.example.bismillahjadi.utils.persistNoteStorage
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.activity_note_list.*

    class NoteListActivity : AppCompatActivity(), View.OnClickListener {

        companion object{
            val TAG: String = NoteListActivity::class.java.simpleName
        }

        // create les vars
        lateinit var notes : MutableList<Note>
        lateinit var adapter: NoteAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_note_list)

            setSupportActionBar(toolbar)


            createNoteFab.setOnClickListener(this)


                notes = loadNotesStorage(this)


            if(notes.isEmpty()) {
                notes.add(Note("Tambah", "Tambahkan Catatan"))
            }


            adapter = NoteAdapter(notes,this)
            noteRV.layoutManager = LinearLayoutManager(this)
            noteRV.adapter = adapter
        }

        override fun onClick(itemView: View) {
            Log.i(TAG, "onClick: Click")
            if (itemView.tag != null) {
                showNote(itemView.tag as Int)
            }
            when(itemView.id){
                R.id.createNoteFab -> createNewnote()
            }
        }

        private fun createNewnote() {
            showNote(-1)
        }

        private fun showNote(noteIndex : Int) {
            val note = if(noteIndex < 0) Note() else notes[noteIndex]
            val intent = Intent(this, NoteDetailActivity::class.java)
            intent.putExtra(NoteDetailActivity.EXTRA_NOTE,note as Parcelable)
            intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)
            startActivityForResult(intent,NoteDetailActivity.REQUEST_EDIT_NOTE)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (resultCode != Activity.RESULT_OK || data ==null) {
                return
            }
            when (requestCode) {
                NoteDetailActivity.REQUEST_EDIT_NOTE -> editNoteResult(data)
            }
        }

        private fun editNoteResult(data: Intent) {
            val indexNote = data.getIntExtra(NoteDetailActivity.EXTRA_NOTE_INDEX,-1)


            when (data.action){
                NoteDetailActivity.ACTION_SENDING_NEW_NOTE_VALUE -> {
                    val note = data.getParcelableExtra<Note>(NoteDetailActivity.EXTRA_NOTE)
                    saveNote(note, indexNote)
                }
                NoteDetailActivity.ACTION_DELETE_NOTE -> {
                    deleteNote(indexNote)
                }
            }
        }

        private fun saveNote(note: Note?, indexNote: Int) {
            persistNoteStorage(this,note!!)

            if (indexNote < 0) {

                notes.add(0, note)
            } else {
                notes[indexNote] = note
            }
            adapter.notifyDataSetChanged()
        }

        private fun deleteNote(indexNote: Int) {

            if (indexNote < 0) {
                return
            }
            val note = notes.removeAt(indexNote)

                    deleteNoteStorage(this,note)
                    adapter.notifyDataSetChanged()

            Snackbar.make(coodinatorLayoutListNote, "${note.title} delete", Snackbar.LENGTH_LONG)
                .show()
        }
    }