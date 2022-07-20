package com.example.themovies.screens.tv.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.network.data.Season
import com.example.themovies.databinding.ListItemSeasonsBinding

class SeasonAdapter (private val seasons: List<Season>): RecyclerView.Adapter<TvSeasonHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvSeasonHolder {
        return TvSeasonHolder(
            ListItemSeasonsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TvSeasonHolder, position: Int) {
        holder.bind(seasons[position])
    }

    override fun getItemCount(): Int = seasons.size
}