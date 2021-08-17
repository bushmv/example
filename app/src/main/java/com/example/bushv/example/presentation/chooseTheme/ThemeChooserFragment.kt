package com.example.bushv.example.presentation.chooseTheme

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.bushv.example.R
import com.example.bushv.example.databinding.FragThemeChooserBinding
import com.example.bushv.example.domain.entity.Theme
import com.example.bushv.example.presentation.ChooserThemeViewModel
import com.example.bushv.example.utility.extentions.fadeInAnimate
import com.example.bushv.example.utility.extentions.scaleInAnimate

class ThemeChooserFragment: Fragment() {

    private lateinit var binding: FragThemeChooserBinding
    private lateinit var adapter: ThemeViewPagerAdapter
    private lateinit var dots: Dots // dots that show the current page as selected and others as unselected
    private val viewModel: ChooserThemeViewModel by viewModels()
    private var needAnimate: Boolean = false // when return from ThemeFragment, need animate card

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
            adapter = ThemeViewPagerAdapter(viewModel.themes) { pair, theme -> themeHasChosen(pair, theme) }
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
                if (needAnimate) {
                    val holderView = binding.themeViewPager.findViewWithTag<View>("$position")
                    holderView.findViewById<GridLayout>(R.id.grid_layout).fadeInAnimate()
                    holderView.findViewById<TextView>(R.id.show_theme_info).fadeInAnimate()
                    holderView.findViewById<Button>(R.id.start_theme).scaleInAnimate()
                    needAnimate = false
                }
                val themeJustCompleted = adapter.themeJustFinished(position)
                if (themeJustCompleted) animateReplacingTheme(position)
            }
        })
    }

    private fun animateReplacingTheme(position: Int) {
        binding.themeViewPager.isUserInputEnabled = false
        val completedTheme = adapter.currentTheme(position)
        val hasNext = viewModel.hasNextTheme()
        viewModel.themeJustCompleted(completedTheme)
        val holderView = binding.themeViewPager.findViewWithTag<View>("$position")
        holderView.findViewById<TextView>(R.id.completedText).animate()
            .scaleY(2f)
            .scaleX(2f)
            .alpha(1f)
            .setDuration(1000)
            .withEndAction {
                holderView.animate()
                    .scaleX(0.2f)
                    .scaleY(0.2f)
                    .alpha(0f)
                    .setDuration(500)
                    .withEndAction {
                        holderView.findViewById<TextView>(R.id.completedText).apply {
                            scaleX = 5f
                            scaleY = 5f
                            alpha = 0f
                        }
                        if (hasNext) {
                            adapter.replaceTheme(position)
                        } else {
                            dots.removeDot(position)
                            adapter.removeTheme(position)
                        }
                        holderView.animate()
                            .scaleY(1f)
                            .scaleX(1f)
                            .alpha(1f)
                            .setDuration(500)
                            .withEndAction {
                                binding.themeViewPager.isUserInputEnabled = true
                            }
                    }
            }
            .start()
    }

    private fun themeHasChosen(pair: Pair<TextView, CardView>, theme: Theme) {
        val direction: NavDirections =
            ThemeChooserFragmentDirections.actionThemeChooserFragmentToThemeFragment(theme)

        val extras = FragmentNavigatorExtras(
            pair.first to theme.title,
            pair.second to theme.id.toString()
        )

        findNavController().navigate(direction, extras)
        needAnimate = true
    }

}