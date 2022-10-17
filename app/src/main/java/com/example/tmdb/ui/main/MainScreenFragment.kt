package com.example.tmdb.ui.main

import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFilms()
        initRV()
        setData()
    }

    private fun initRV() {
        binding.rvFilmList.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvFilmList.adapter = adapter
    }

    private fun setData() {
        viewModel.filmList.observe(viewLifecycleOwner) { films ->
            adapter.setData(films)
        }
    }

    companion object {
        private const val SCOPE_ID = "main_screen_fragment_id"
    }
}