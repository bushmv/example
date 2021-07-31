package com.example.bushv.example.presentation.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.bushv.example.R
import com.example.bushv.example.databinding.FragThemeChooserBinding
import com.example.bushv.example.domain.entity.Theme
import com.example.bushv.example.presentation.ChooserThemeViewModel
import com.example.bushv.example.presentation.Dots
import com.example.bushv.example.presentation.ThemeViewPagerPageTransformer
import com.example.bushv.example.presentation.recyclerAdapters.ThemeViewPagerAdapter

class ThemeChooserFragment: Fragment() {

    private lateinit var binding: FragThemeChooserBinding
    private lateinit var adapter: ThemeViewPagerAdapter
    private lateinit var dots: Dots // dots that show the current page as selected and others as unselected
    private val viewModel: ChooserThemeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragThemeChooserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewPager()
        prepareDots()
        registerOnPageChangeCallback()
    }

    private fun prepareViewPager() {
        binding.apply {
            adapter = ThemeViewPagerAdapter(viewModel.themes) { pair, theme -> themeIsChosen(pair, theme) }
            sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.card_transition)
            themeViewPager.adapter = adapter
            themeViewPager.setPageTransformer(ThemeViewPagerPageTransformer())
            postponeEnterTransition()
            themeViewPager.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    private fun prepareDots() {
        dots = Dots(viewModel.themes.size, requireContext(), binding.dots)
    }

    private fun registerOnPageChangeCallback() {
        binding.themeViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                dots.changeSelectedDot(position)
            }
        })
    }

    private fun themeIsChosen(pair: Pair<TextView, CardView>, theme: Theme) {
        val direction: NavDirections = ThemeChooserFragmentDirections.actionThemeChooserFragmentToThemeFragment(theme)

        val extras = FragmentNavigatorExtras(
            pair.first to theme.title,
            pair.second to theme.themeId.toString()
        )

        findNavController().navigate(direction, extras)
    }

}