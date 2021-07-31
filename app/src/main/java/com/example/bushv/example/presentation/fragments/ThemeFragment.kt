package com.example.bushv.example.presentation.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bushv.example.R
import com.example.bushv.example.databinding.FragThemeLayoutBinding
import com.example.bushv.example.domain.entity.EnglishLevel
import com.example.bushv.example.domain.entity.Theme
import com.example.bushv.example.presentation.Event
import com.example.bushv.example.presentation.Keyboard
import com.example.bushv.example.presentation.ThemeViewModel
import com.example.bushv.example.presentation.ThemeViewModelFactory
import com.example.bushv.example.utility.extentions.animateReplacingText
import java.util.concurrent.TimeUnit

class ThemeFragment: Fragment() {

    private lateinit var binding: FragThemeLayoutBinding
    private lateinit var keyBoard: Keyboard
    private lateinit var theme: Theme
    private val viewModel: ThemeViewModel by viewModels() { ThemeViewModelFactory(theme) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme = navArgs<ThemeFragmentArgs>().value.themeArg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragThemeLayoutBinding.inflate(inflater, container, false)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.card_transition)
        postponeEnterTransition(250, TimeUnit.MILLISECONDS)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createKeyboard()
        setTransitionName()
        updateUI()
        registerEventHandler()
    }

    private fun createKeyboard() {
        keyBoard = Keyboard(requireContext(), binding.keyboard.root) { checkUserInput((it as TextView).text.toString().first()) }
        keyBoard.createKeyboard()
        keyBoard.showKeyboardAnimation()
    }

    private fun setTransitionName() {
        binding.apply {
            card.transitionName = theme.themeId.toString()
            themeCardTitle.transitionName = theme.title
        }
    }

    private fun updateUI() {
        binding.apply {
            themeCardTitle.text = theme.title.split("/")[0]
            card.setOnClickListener {
                themeCardSettings.visibility = if (themeCardSettings.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
            isFavoriteExample.setOnClickListener {
                viewModel.changeIsFavorite()
            }
            themeCardSlider.addOnChangeListener { slider, value, fromUser ->
                val s = EnglishLevel.values()[value.toInt() - 1].strColor
                tvExampleDifficult.setTextColor(Color.parseColor(s))
                viewModel.changeDifficult(value)
            }
        }
    }

    private fun registerEventHandler() {
        viewModel.eventLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Event.IsFavoriteChanged -> updateIsFavoriteColor(it.isFavorite)
                is Event.NewExample -> updateCard(it.sentenceRU, it.sentenceEN, it.isFavorite)
                is Event.ProgressChanged -> binding.themeCardProgress.progress = it.newProgress
                Event.ThemeCompleted -> findNavController().popBackStack()
                is Event.UpdateSentenceEN -> { binding.themeCardSentenceEN.text = it.sentenceEN }
            }
        }
    }

    private fun updateIsFavoriteColor(isFavorite: Boolean) {
        if (isFavorite) {
            binding.isFavoriteExample.setColorFilter(Color.RED)
        } else {
            binding.isFavoriteExample.setColorFilter(Color.GRAY)
        }
    }

    private fun updateCard(sentenceRU: SpannableString, sentenceEN: SpannableStringBuilder, isFavorite: Boolean) {
        binding.apply {
            themeCardSentenceEN.animateReplacingText { it.text = sentenceEN }
            themeCardSentenceRU.animateReplacingText { it.text = sentenceRU }
            updateIsFavoriteColor(isFavorite)
        }
    }

    private fun checkUserInput(char: Char) {
        viewModel.check(char)
    }

    override fun onResume() {
        super.onResume()
        viewModel.prepareNextExample()
    }

}