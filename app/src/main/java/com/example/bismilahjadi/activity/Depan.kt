package com.example.bismilahjadi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bismilahjadi.NoteListActivity
import com.example.bismilahjadi.R
import kotlinx.android.synthetic.main.activity_depan.*

class Depan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_depan)

        button.setOnClickListener{
           val go = Intent(this@Depan, ListSurahActivity::class.java)
                startActivity(go)

        }
    }
}