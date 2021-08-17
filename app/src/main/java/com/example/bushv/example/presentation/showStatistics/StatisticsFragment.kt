package com.example.bushv.example.presentation.showStatistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bushv.example.data.AppPref
import com.example.bushv.example.databinding.FragStatisticsBinding
import com.example.bushv.example.domain.entity.EnglishLevel
import kotlin.math.roundToInt

class StatisticsFragment: Fragment() {

    private lateinit var binding: FragStatisticsBinding
    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            cardStatisticsCount.text = "${AppPref.totalCompletedExamplesInApp} / ${AppPref.totalExamplesInApp}"
            val percentWordCompleted =
                if (AppPref.totalExamplesInApp > 0) {
                        (AppPref.totalCompletedExamplesInApp * 100f / AppPref.totalExamplesInApp).roundToInt()
                } else {
                    Toast.makeText(requireContext(), "похоже при вычислении процента пройденных примеров что то пошло не так", Toast.LENGTH_SHORT).show()
                    0
                }

            cardStatisticsPercent.text = "$percentWordCompleted %"

            beginnerStatistics.text = viewModel.progressStringFor(EnglishLevel.BEGINNER)
            elementaryStatistics.text = viewModel.progressStringFor(EnglishLevel.ELEMENTARY)
            intermediateStatistics.text = viewModel.progressStringFor(EnglishLevel.INTERMEDIATE)
            upperIntermediateStatistics.text = viewModel.progressStringFor(EnglishLevel.UPPER_INTERMEDIATE)
            advancedStatistics.text = viewModel.progressStringFor(EnglishLevel.ADVANCED)

            beginnerStatisticsProgress.progress = viewModel.progressFor(EnglishLevel.BEGINNER)
            elementaryStatisticsProgress.progress = viewModel.progressFor(EnglishLevel.ELEMENTARY)
            intermediateStatisticsProgress.progress = viewModel.progressFor(EnglishLevel.INTERMEDIATE)
            upperIntermediateStatisticsProgress.progress = viewModel.progressFor(EnglishLevel.UPPER_INTERMEDIATE)
            advancedStatisticsProgress.progress = viewModel.progressFor(EnglishLevel.ADVANCED)

            val weekStat = viewModel.weekStatistics()
            binding.apply {
                val scale = requireContext().resources.displayMetrics.density
                monColumn.layoutParams.height = (scale * weekStat[0].height).roundToInt()
                monTitle.text = weekStat[0].title
                tueColumn.layoutParams.height = (scale * weekStat[1].height).roundToInt()
                tueTitle.text = weekStat[1].title
                wedColumn.layoutParams.height = (scale * weekStat[2].height).roundToInt()
                wedTitle.text = weekStat[2].title
                thuColumn.layoutParams.height = (scale * weekStat[3].height).roundToInt()
                thuTitle.text = weekStat[3].title
                friColumn.layoutParams.height = (scale * weekStat[4].height).roundToInt()
                friTitle.text = weekStat[5].title
                satColumn.layoutParams.height = (scale * weekStat[5].height).roundToInt()
                satTitle.text = weekStat[5].title
                sunColumn.layoutParams.height = (scale * weekStat[6].height).roundToInt()
                sunTitle.text = weekStat[6].title
                root.requestLayout()
            }
        }
    }
}