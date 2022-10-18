package com.example.tmdb.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.data.filmList.FilmListDTO

class RVMainScreenAdapter() :
    RecyclerView.Adapter<RVMainScreenAdapter.MainScreenViewHolder>() {

    private val data = mutableListOf<FilmListDTO.Result>()

    fun setData(result: FilmListDTO, pageId: Int) {
        if (pageId == 1) {
            data.clear()
        }
        data.addAll(result.results)
        if (pageId == 1) {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainScreenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_film_list, parent, false)
        return MainScreenViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainScreenViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MainScreenViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val image: AppCompatImageView =
            itemView.findViewById(R.id.rv_item_film_list_image)
        private val name: AppCompatTextView =
            itemView.findViewById(R.id.rv_item_film_list_name)
        private val date: AppCompatTextView =
            itemView.findViewById(R.id.rv_item_film_list_date)

        fun bind(data: FilmListDTO.Result) {
            val imageUrl = "https://www.themoviedb.org/t/p/original${data.posterPath}"
//            Glide.with(itemView)
//                .load(imageUrl)
//                .centerCrop()
//                .error(R.drawable.ic_baseline_terrain_24)
//                .into(image)
            name.text = data.title
            date.text = data.releaseDate

            itemView.setOnClickListener {

            }
        }
    }
}