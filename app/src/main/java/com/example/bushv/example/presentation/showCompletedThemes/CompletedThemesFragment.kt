package com.example.bushv.example.presentation.showCompletedThemes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bushv.example.data.AppPref
import com.example.bushv.example.databinding.FragCompletedThemesBinding
import com.example.bushv.example.domain.entity.EnglishLevel
import com.example.bushv.example.domain.entity.Theme

class CompletedThemesFragment: Fragment() {

    private lateinit var binding: FragCompletedThemesBinding
    private lateinit var adapter: CompletedThemesAdapter
    private val viewModel: CompletedThemesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragCompletedThemesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextToChips()
        registerListeners()
        prepareRecyclerView()
        viewModel.completedThemesLiveData.observe(viewLifecycleOwner) {
            adapter.changeThemes(it)
        }
    }

    private fun setTextToChips() {
        binding.apply {
            beginner.text = completedAndTotalString(EnglishLevel.BEGINNER)
            elementary.text = completedAndTotalString(EnglishLevel.ELEMENTARY)
            intermediate.text = completedAndTotalString(EnglishLevel.INTERMEDIATE)
            upperIntermediate.text = completedAndTotalString(EnglishLevel.UPPER_INTERMEDIATE)
            advanced.text = completedAndTotalString(EnglishLevel.ADVANCED)
        }
    }

    private fun registerListeners() {
        binding.apply {
            beginner.setOnClickListener { viewModel.loadThemesFor(EnglishLevel.BEGINNER) }
            elementary.setOnClickListener { viewModel.loadThemesFor(EnglishLevel.ELEMENTARY) }
            intermediate.setOnClickListener { viewModel.loadThemesFor(EnglishLevel.INTERMEDIATE) }
            upperIntermediate.setOnClickListener { viewModel.loadThemesFor(EnglishLevel.UPPER_INTERMEDIATE) }
            advanced.setOnClickListener { viewModel.loadThemesFor(EnglishLevel.ADVANCED) }
        }
    }

    private fun prepareRecyclerView() {
        binding.apply {
            adapter = CompletedThemesAdapter(arrayListOf()) { moreInfoAboutCompletedTheme(it) }
            completedThemesRecyclerView.adapter = adapter
            val linearLayoutManager = LinearLayoutManager(requireContext())
            completedThemesRecyclerView.layoutManager = linearLayoutManager
            completedThemesRecyclerView.addItemDecoration(
                DividerItemDecoration(requireContext(), linearLayoutManager.orientation)
            )
        }
    }

    private fun moreInfoAboutCompletedTheme(theme: Theme) {
        val direction: NavDirections = CompletedThemesFragmentDirections.actionCompletedThemesFragmentToDetailedCompletedTheme(theme)
        findNavController().navigate(direction)
    }

    private fun completedAndTotalString(englishLevel: EnglishLevel): String {
        return when(englishLevel) {
            EnglishLevel.BEGINNER ->
                "${englishLevel.name} (${AppPref.beginnerCompletedThemesCount}/${AppPref.beginnerThemesCount})"
            EnglishLevel.ELEMENTARY ->
                "${englishLevel.name} (${AppPref.elementaryCompletedThemesCount}/${AppPref.elementaryThemesCount})"
            EnglishLevel.INTERMEDIATE ->
                "${englishLevel.name} (${AppPref.intermediateCompletedThemesCount}/${AppPref.intermediateThemesCount})"
            EnglishLevel.UPPER_INTERMEDIATE ->
                "${englishLevel.name} (${AppPref.upperIntermediateCompletedThemesCount}/${AppPref.upperIntermediateThemesCount})"
            EnglishLevel.ADVANCED ->
                "${englishLevel.name} (${AppPref.advancedCompletedThemesCount}/${AppPref.advancedThemesCount})"
        }
    }
}