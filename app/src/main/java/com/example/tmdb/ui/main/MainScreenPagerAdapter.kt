package com.example.tmdb.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb.data.filmList.FilmListDTO
import com.example.tmdb.databinding.RvItemFilmListBinding

class MainScreenPagerAdapter :
    PagingDataAdapter<
            FilmListDTO.Result,
            MainScreenPagerAdapter.FilmListViewHolder
            >(FilmListComparator) {

    class FilmListViewHolder(val view: RvItemFilmListBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    object FilmListComparator: DiffUtil.ItemCallback<FilmListDTO.Result>() {
        override fun areItemsTheSame(
            oldItem: FilmListDTO.Result,
            newItem: FilmListDTO.Result
        ): Boolean {
            return oldItem.originalTitle == newItem.originalTitle
        }

        override fun areContentsTheSame(
            oldItem: FilmListDTO.Result,
            newItem: FilmListDTO.Result
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: FilmListViewHolder, position: Int) {
        val film = getItem(position)
        holder.view.rvItemFilmListName.text = film?.originalTitle.toString()
        holder.view.rvItemFilmListDate.text = film?.releaseDate.toString()
        Glide.with(holder.view.root)
            .load("https://image.tmdb.org/t/p/w300"+ film?.posterPath.toString())
            .centerCrop()
            .error(com.example.tmdb.R.drawable.ic_baseline_terrain_24)
            .into(holder.view.rvItemFilmListImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemFilmListBinding.inflate(inflater, parent, false)
        return FilmListViewHolder(binding)
    }
}