package com.bush_example_app.bushv.example.presentation.showSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.bush_example_app.bushv.example.data.AppPref
import com.bush_example_app.bushv.example.databinding.FragSettingsBinding

class SettingsFragment: Fragment() {

    private lateinit var binding: FragSettingsBinding
    private lateinit var current: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerListeners()
    }

    private fun registerListeners() {
        when (AppPref.colorStr) {
            "#006800" -> { current = binding.blueColor }
            "#FF9900" -> { current = binding.greenColor }
            "#0000CC" -> { current = binding.orangeColor }
            "#7A00CC" -> { current = binding.purpleColor }
            "#FF0000" -> { current = binding.redColor }
            "#00FFFF" -> { current = binding.turquoiseColor }
        }
        current.scaleX = 1.2f
        current.scaleY = 1.2f
        binding.apply {
            blueColor.setOnClickListener { switchColor(it)  }
            greenColor.setOnClickListener { switchColor(it) }
            orangeColor.setOnClickListener { switchColor(it) }
            purpleColor.setOnClickListener { switchColor(it) }
            redColor.setOnClickListener { switchColor(it) }
            turquoiseColor.setOnClickListener { switchColor(it) }

            showSplashScreen.isChecked = AppPref.showSplashScreen
            showSplashScreen.setOnCheckedChangeListener { _, isChecked -> AppPref.showSplashScreen = isChecked }
        }

        (binding.englishLevelRadioGroup[AppPref.startEnglishLevel] as RadioButton).isChecked = true
        (binding.englishLevelRadioGroup[0] as RadioButton).apply {
            if (!isChecked) jumpDrawablesToCurrentState()
        }

        binding.englishLevelRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                binding.beginner.id -> AppPref.startEnglishLevel = 0
                binding.elementary.id -> AppPref.startEnglishLevel = 1
                binding.intermediate.id -> AppPref.startEnglishLevel = 2
                binding.upperIntermediate.id -> AppPref.startEnglishLevel = 3
                binding.advanced.id -> AppPref.startEnglishLevel = 4
            }
        }
    }

    private fun switchColor(view: View) {
        AppPref.colorStr = view.tag as String
        current.animate()
            .scaleX(0.8f)
            .scaleY(0.8f)
            .setDuration(500)
            .start()
        current = view
        current.animate()
            .scaleX(1.2f)
            .scaleY(1.2f)
            .setDuration(500)
            .start()
    }
}