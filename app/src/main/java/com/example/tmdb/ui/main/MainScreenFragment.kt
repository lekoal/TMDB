package com.example.tmdb.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.FragmentMainScreenBinding
import com.example.tmdb.utils.ViewBindingFragment
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class MainScreenFragment :
    ViewBindingFragment<FragmentMainScreenBinding>(FragmentMainScreenBinding::inflate) {

    private val scope by lazy {
        getKoin().getOrCreateScope<MainScreenFragment>(SCOPE_ID)
    }

    private val viewModel: MainScreenViewModel by lazy {
        scope.get(named("main_screen_view_model"))
    }

    private val adapter: RVMainScreenAdapter by lazy {
        scope.get(named("main_screen_adapter"))
    }

    private lateinit var layoutManager: LinearLayoutManager
    private var visibleItemCount = 0
    private var pastVisibleItemCount = 0
    private var totalItemCount = 0
    private var pageId = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )

        viewModel.getFilms(pageId)
        initRV()
        setData()
        loadingCheck()
        errorCheck()
    }

    private fun initRV() {
        binding.rvFilmList.layoutManager = layoutManager
        binding.rvFilmList.adapter = adapter

        binding.rvFilmList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dx > 0) {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItemCount = layoutManager.findFirstVisibleItemPosition()
                    if ((visibleItemCount + pastVisibleItemCount) >= totalItemCount) {
                        ++pageId
                        viewModel.getFilms(pageId)
                        setData()
                    }
                }
            }
        })
    }

    private fun setData() {
        viewModel.filmList.observe(viewLifecycleOwner) { films ->
            adapter.setData(films, pageId)
            if (pageId > 1) {
                binding.rvFilmList.post { adapter.notifyItemInserted(adapter.itemCount - 1) }
            }
        }
    }

    private fun progressManager(show: Boolean) {
        if (show) {
            binding.loadingProcessLayout.visibility = View.VISIBLE
        } else {
            binding.loadingProcessLayout.visibility = View.GONE
        }
        binding.loadingProcessLayout.isClickable = show
    }

    private fun loadingCheck() {
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                progressManager(true)
            } else {
                progressManager(false)
            }
        }
    }

    private fun errorCheck() {
        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error) {
                viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private const val SCOPE_ID = "main_screen_fragment_id"
    }
}