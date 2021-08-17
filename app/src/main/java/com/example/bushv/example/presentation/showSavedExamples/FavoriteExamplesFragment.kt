package com.example.bushv.example.presentation.showSavedExamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bushv.example.databinding.FragSavedExamplesBinding

class FavoriteExamplesFragment : Fragment() {

    private lateinit var binding: FragSavedExamplesBinding
    private lateinit var adapter: SavedExamplesAdapter
    private val viewModel: FavoriteExamplesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragSavedExamplesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareAdapter()
        viewModel.favoriteExamples.observe(viewLifecycleOwner) {
            adapter.changeData(it)
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })
    }

    private fun prepareAdapter() {
        binding.apply {
            adapter = SavedExamplesAdapter(arrayListOf())
            val linearLayoutManager = LinearLayoutManager(requireContext())
            favoritesExamplesRecyclerView.layoutManager = linearLayoutManager
            favoritesExamplesRecyclerView.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavorites()
    }
}