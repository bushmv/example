package com.bush_example_app.bushv.example.presentation.showDetailCompletedTheme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bush_example_app.bushv.example.databinding.FragDetailedCompletedThemeBinding
import com.bush_example_app.bushv.example.domain.entity.Theme
import com.bush_example_app.bushv.example.presentation.startTheme.ThemeFragmentArgs


class DetailedCompletedTheme: Fragment() {

    private lateinit var binding: FragDetailedCompletedThemeBinding
    private val viewModel: DetailCompletedThemeViewModel by viewModels { CompletedExamplesViewModelFactory(theme) }
    private lateinit var theme: Theme
    private lateinit var adapter: CompletedExamplesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme = navArgs<ThemeFragmentArgs>().value.themeArg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragDetailedCompletedThemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            titleEn.text = theme.titleEN()
            titleRu.text = "(${theme.titleRU()})"
            themeDescription.text = theme.info
            adapter = CompletedExamplesAdapter(arrayListOf())
            completedExamples.adapter = adapter
            completedExamples.layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.examplesLiveDate.observe(viewLifecycleOwner) {
            adapter.changeExamples(it)
        }
    }

}