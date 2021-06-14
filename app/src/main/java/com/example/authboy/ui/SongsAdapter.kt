package com.example.authboy.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.authboy.R
import com.example.authboy.models.Song
import kotlinx.android.synthetic.main.cardview_item.view.*

class SongsAdapter(val songs: List<Song>): RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_item, parent, false), mListener
        )
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]

        holder.view.name_ic.text = song.name_song
    }

    override fun getItemCount() = songs.size

    class SongViewHolder(val view: View, listener: onItemClickListener): RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener { listener.onItemClick(absoluteAdapterPosition) }
        }
    }
}