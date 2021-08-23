package com.bush_example_app.bushv.example.presentation.showAppExplanation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bush_example_app.bushv.example.R
import com.bush_example_app.bushv.example.databinding.FragAppExplanationBinding
import com.bush_example_app.bushv.example.presentation.chooseTheme.Dots

const val explanationCardsCount = 5

class AppExplanationFragment: Fragment() {

    lateinit var binding: FragAppExplanationBinding
    lateinit var adapter: ExplanationAdapter
    private lateinit var dots: Dots
    private var completed = false
    lateinit var closeSplashScreenCallback: () -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragAppExplanationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dots = Dots(explanationCardsCount, requireContext(), binding.dotsContainer)
        binding.apply {
            complete.setOnClickListener {
                findNavController().popBackStack()
            }
            adapter = ExplanationAdapter(explanations())
            explanationViewPager.adapter = adapter
            explanationViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    dots.changeSelectedDot(position)
                    if (!completed && position == explanationCardsCount - 1) {
                        completed = true
                        binding.complete.isClickable = true
                        binding.dotsContainer.animate()
                            .alpha(0f)
                            .translationYBy(500f)
                            .setDuration(1000)
                            .start()
                        binding.complete.translationY = 500f
                        binding.complete.animate()
                            .translationYBy(-500f)
                            .alpha(1f)
                            .setDuration(1000)
                            .start()
                    }
                }
            })
            explanationViewPager.setPageTransformer(ExplanationPagerTransformer())
        }
    }

    private fun explanations(): ArrayList<Explanation> {
        return arrayListOf(
            Explanation(R.drawable.exp1, getString(R.string.explanation_1)),
            Explanation(R.drawable.exp2, getString(R.string.explanation_2)),
            Explanation(R.drawable.exp3, getString(R.string.explanation_3)),
            Explanation(R.drawable.exp4, getString(R.string.explanation_4)),
            Explanation(R.drawable.exp5, getString(R.string.explanation_5))
        )
    }
}