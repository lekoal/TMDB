package com.example.tmdb.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.FragmentMainScreenBinding
import com.example.tmdb.utils.ViewBindingFragment
import kotlinx.coroutines.launch
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

    private val adapter: MainScreenPagerAdapter by lazy {
        scope.get(named("main_screen_adapter"))
    }

    private lateinit var layoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )

        initRV()
        loadingCheck()
        errorCheck()
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        lifecycleScope.launch {
            viewModel.getFilmList().observe(viewLifecycleOwner) {
                it?.let {
                    adapter.submitData(lifecycle, it)
                }
            }
        }
        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            ) {
                progressManager(true)
            } else {
                progressManager(false)
                val errorState = when {
                    loadState.append is LoadState.Error -> {
                        loadState.append as LoadState.Error
                    }
                    loadState.prepend is LoadState.Error -> {
                        loadState.prepend as LoadState.Error
                    }
                    loadState.refresh is LoadState.Error -> {
                        loadState.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initRV() {
        binding.rvFilmList.layoutManager = layoutManager
        binding.rvFilmList.adapter = adapter
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