package com.example.bismillahjadi.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.example.bismilahjadi.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*


private val TAG = "storage"

fun persistNoteStorage(context : Context, note : Note) {
    if(TextUtils.isEmpty(note.fileName)){
        note.fileName = UUID.randomUUID().toString() + ".notesP"
    }


    val fileOutput = context.openFileOutput(note.fileName,Context.MODE_PRIVATE)

    val outPutStream = ObjectOutputStream(fileOutput)

    outPutStream.writeObject(note)

    outPutStream.close()
}


private fun loadNoteStorage(context : Context, filename : String) : Note {

    val fileInput = context.openFileInput(filename)
    val inputStream = ObjectInputStream(fileInput)
    val note = inputStream.readObject() as Note

    return note
}

fun loadNotesStorage(context: Context) : MutableList<Note> {
    val notes = mutableListOf<Note>()

    val notesDir = context.filesDir
    for (filename in notesDir.list()) {
        val note = loadNoteStorage(context, filename)
        Log.i(TAG, "loadNotes: note = $note")
        notes.add(note)
       }

    return notes
}

fun deleteNoteStorage(context: Context, note: Note) {
    context.deleteFile(note.fileName)
}