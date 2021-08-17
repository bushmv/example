package com.example.bushv.example.presentation.showAppExplanation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.bushv.example.R
import com.example.bushv.example.databinding.FragAppExplanationBinding
import com.example.bushv.example.presentation.chooseTheme.Dots

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
            Explanation(R.drawable.expanation1, "Выбери категорию по описанию"),
            Explanation(R.drawable.exp2, "просто повтори предложение"),
            Explanation(R.drawable.exp3, "выбери уровень сложности и повтори предложение еще раз"),
            Explanation(R.drawable.exp4, "сохраняй понравившиеся примеры"),
            Explanation(R.drawable.exp5, "улучшай свой английский без заучиваний и страданий")
        )
    }
}