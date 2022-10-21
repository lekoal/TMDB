package com.example.tmdb.ui.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentDetailsBinding
import com.example.tmdb.utils.ApiUtils
import com.example.tmdb.utils.ViewBindingFragment
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import kotlin.time.Duration.Companion.minutes

class DetailsFragment :
    ViewBindingFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val scope by lazy {
        getKoin().getOrCreateScope<DetailsFragment>(SCOPE_ID)
    }

    private val viewModel: DetailsViewModel by lazy {
        scope.get(named("details_view_model"))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        initializeViewModel()
    }

    companion object {
        private const val SCOPE_ID = "details_fragment_scope_id"
    }

    private fun setData() {

        val backDropImage = binding.detailsBackdropImage
        val filmTitle = binding.detailsTitle
        val posterImage = binding.detailsPosterImage
        val releaseDate = binding.detailsReleaseDate
        val filmLength = binding.detailsLength
        val filmGenres = binding.detailsGenres
        val scoreProgress = binding.detailsUserScore
        val scoreText = binding.rateText
        val filmOverview = binding.detailsOverviewText

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                progressManager(true)
            } else {
                progressManager(false)
            }
        }
        viewModel.error.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.filmDetails.observe(viewLifecycleOwner) { det ->
            Glide.with(this)
                .load(ApiUtils.imageBaseUrl + det.backdropPath)
                .centerCrop()
                .error(R.color.black)
                .into(backDropImage)
            filmTitle.text = det.originalTitle
            Glide.with(this)
                .load(ApiUtils.imageBaseUrl + det.posterPath)
                .centerCrop()
                .into(posterImage)
            releaseDate.text = det.releaseDate
            filmLength.text = det.runtime.minutes.toString()
            val genres = mutableListOf<String>()
            det.genres.forEach { genre ->
                genres.add(genre.name)
            }
            filmGenres.text = genres.joinToString(", ")
            scoreProgress.progress = (det.voteAverage.toInt() * 10)
            if ((det.voteAverage.toInt() * 10) < 50) {
                scoreProgress.
                setIndicatorColor(ContextCompat.getColor(requireContext(), R.color.lime_600))
            } else {
                scoreProgress.
                setIndicatorColor(ContextCompat.getColor(requireContext(), R.color.green_500))
            }
            scoreText.text = (det.voteAverage.toInt() * 10).toString()
            filmOverview.text = det.overview
        }

    }

    private fun initializeViewModel() {
        val filmId = DetailsFragmentArgs.fromBundle(requireArguments()).filmId
        viewModel.getDetails(filmId)
    }

    private fun progressManager(show: Boolean) {
        if (show) {
            binding.loadingProcessLayout.visibility = View.VISIBLE
        } else {
            binding.loadingProcessLayout.visibility = View.GONE
        }
        binding.loadingProcessLayout.isClickable = show
    }
}