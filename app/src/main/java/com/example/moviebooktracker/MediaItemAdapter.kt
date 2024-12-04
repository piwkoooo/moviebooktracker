package com.example.moviebooktracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MediaItemAdapter(
    private val mediaList: MutableList<MediaItem>
) : RecyclerView.Adapter<MediaItemAdapter.MediaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_media, parent, false)
        return MediaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val item = mediaList[position]
        holder.titleTextView.text = item.title
        holder.genreTextView.text = item.genre
        holder.ratingTextView.text = "Ocena: ${item.rating}/10"
        holder.typeTextView.text = item.type
        holder.reviewTextView.text = item.review  // Przypisz recenzję do TextView
        holder.watchedCheckBox.isChecked = item.isWatchedOrRead

        holder.watchedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            item.isWatchedOrRead = isChecked
        }
    }

    override fun getItemCount(): Int = mediaList.size

    class MediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val genreTextView: TextView = itemView.findViewById(R.id.genreTextView)
        val ratingTextView: TextView = itemView.findViewById(R.id.ratingTextView)
        val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
        val reviewTextView: TextView = itemView.findViewById(R.id.reviewTextView) // Dodaj pole recenzji
        val watchedCheckBox: CheckBox = itemView.findViewById(R.id.watchedCheckBox)
    }
}
