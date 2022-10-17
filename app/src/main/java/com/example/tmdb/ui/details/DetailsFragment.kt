package com.example.tmdb.ui.details

import com.example.tmdb.databinding.FragmentDetailsBinding
import com.example.tmdb.utils.ViewBindingFragment

class DetailsFragment :
    ViewBindingFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {



    companion object {
        fun newInstance() = DetailsFragment()
    }
}