package com.example.moviebooktracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var mediaRecyclerView: RecyclerView
    private lateinit var addButton: Button
    private val mediaList = mutableListOf<MediaItem>()
    private lateinit var adapter: MediaItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaRecyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.addButton)

        loadMediaList()

        adapter = MediaItemAdapter(mediaList)
        mediaRecyclerView.adapter = adapter
        mediaRecyclerView.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener { showAddItemDialog() }
    }

    private fun showAddItemDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_item, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Dodaj pozycję")
            .setView(dialogView)
            .setPositiveButton("Dodaj") { _, _ ->
                val title = dialogView.findViewById<EditText>(R.id.titleEditText).text.toString()
                val genre = dialogView.findViewById<EditText>(R.id.genreEditText).text.toString()
                val review = dialogView.findViewById<EditText>(R.id.reviewEditText).text.toString()
                val rating = dialogView.findViewById<SeekBar>(R.id.ratingSeekBar).progress
                val type = if (dialogView.findViewById<RadioButton>(R.id.radioFilm).isChecked) "Film" else "Książka"

                mediaList.add(MediaItem(title, genre, review, rating, false, type))
                adapter.notifyItemInserted(mediaList.size - 1)
                saveMediaList()
            }
            .setNegativeButton("Anuluj", null)
            .create()

        dialog.show()
    }

    private fun loadMediaList() {
        val sharedPreferences = getSharedPreferences("media_prefs", MODE_PRIVATE)
        val json = sharedPreferences.getString("media_list", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<MediaItem>>() {}.type
            mediaList.addAll(Gson().fromJson(json, type))
        }
    }

    private fun saveMediaList() {
        val sharedPreferences = getSharedPreferences("media_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("media_list", Gson().toJson(mediaList))
        editor.apply()
    }
}
