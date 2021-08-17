package com.example.bushv.example.presentation.startTheme

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bushv.example.R
import com.example.bushv.example.databinding.FragThemeLayoutBinding
import com.example.bushv.example.domain.entity.EnglishLevel
import com.example.bushv.example.domain.entity.Theme
import com.example.bushv.example.presentation.ThemeViewModelFactory
import com.example.bushv.example.utility.extensions.animateReplacingText
import java.util.*
import java.util.concurrent.TimeUnit

class ThemeFragment : Fragment() {

    private lateinit var binding: FragThemeLayoutBinding
    private lateinit var keyBoard: Keyboard
    private lateinit var theme: Theme
    private lateinit var progressAnimation: ProgressBarAnimation
    private val viewModel: ThemeViewModel by viewModels { ThemeViewModelFactory(theme) }
    private var isNewFragment = true
    private lateinit var textToSpeech: TextToSpeech

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
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.card_transition)
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
        keyBoard = Keyboard(
            requireContext(),
            binding.keyboard.root
        ) { checkUserInput((it as TextView).text.toString().first()) }
        keyBoard.createKeyboard()
        keyBoard.showKeyboardAnimation()
    }

    private fun setTransitionName() {
        binding.apply {
            card.transitionName = theme.id.toString()
            themeCardTitle.transitionName = theme.title
        }
    }

    private fun updateUI() {
        binding.apply {
            progressAnimation = ProgressBarAnimation(binding.themeCardProgress, 0, 0)
            progressAnimation.duration = 500
            themeCardProgress.startAnimation(progressAnimation)
            progressAnimation.startOffset = 0
            themeCardTitle.text = theme.titleEN()
            card.setOnClickListener {
                themeCardSettings.visibility =
                    if (themeCardSettings.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
            isFavoriteExample.setOnClickListener {
                viewModel.changeIsFavorite()
            }
            themeCardSlider.addOnChangeListener { slider, value, _ ->
                val s = EnglishLevel.values()[value.toInt() - 1].strColor
                slider.trackActiveTintList = ColorStateList.valueOf(Color.parseColor(s))
                slider.thumbTintList = ColorStateList.valueOf(Color.parseColor(s))
                viewModel.changeDifficult(value)
            }
            themeCardProgress.animate()
                .scaleX(1f)
                .setDuration(750)
                .start()
            themeCardSlider.setLabelFormatter { value ->
                when (value) {
                    1f -> getString(R.string.very_easy)
                    2f -> getString(R.string.easy)
                    3f -> getString(R.string.middle)
                    4f -> getString(R.string.hard)
                    5f -> getString(R.string.very_hard)
                    else -> throw IllegalStateException("Slider mush change value from 1 to 5")
                }
            }
            textToSpeech = TextToSpeech(requireContext()) {
                if (it == TextToSpeech.SUCCESS) {
                    textToSpeech.language = Locale.ENGLISH
                    textToSpeech.setPitch(1.3f)
                    textToSpeech.setSpeechRate(1f)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "К сожалению, голосовое воспроизведение недоступно на данном устройстве.", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            imageSayExampleEn.setOnClickListener {
                textToSpeech.speak(
                    binding.themeCardSentenceEN.text,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    binding.themeCardSentenceEN.text.hashCode().toString()
                )
            }
        }
    }

    private fun registerEventHandler() {
        viewModel.eventLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Event.IsFavoriteChanged -> updateIsFavoriteColor(it.isFavorite)
                is Event.NewExample -> updateCard(it.sentenceRU, it.sentenceEN, it.isFavorite)
                is Event.ProgressChanged -> {
                    if (it.newProgress > 0) {
                        progressAnimation.changeRange(it.newProgress)
                        binding.themeCardProgress.startAnimation(progressAnimation)
                        changeSliderVisibility(it.newProgress)
                    }
                }
                is Event.ThemeCompleted -> findNavController().popBackStack()
                is Event.UpdateSentenceEN -> {
                    binding.themeCardSentenceEN.text = it.sentenceEN
                }
            }
        }
    }

    private fun updateIsFavoriteColor(isFavorite: Boolean) {
        if (isFavorite) {
            binding.isFavoriteExample.scaleX = 0f
            binding.isFavoriteExample.scaleY = 0f
            binding.isFavoriteExample.setColorFilter(Color.RED)
            binding.isFavoriteExample.animate()
                .scaleX(1.3f)
                .scaleY(1.3f)
                .setDuration(300)
                .withEndAction {
                    binding.isFavoriteExample.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(200)
                }.start()
        } else {
            binding.isFavoriteExample.setColorFilter(Color.BLACK)
        }
    }

    private fun updateCard(
        sentenceRU: SpannableString,
        sentenceEN: SpannableStringBuilder,
        isFavorite: Boolean
    ) {
        binding.apply {
            themeCardSentenceEN.animateReplacingText { it.text = sentenceEN }
            themeCardSentenceRU.animateReplacingText { it.text = sentenceRU }
            if (isFavorite) {
                binding.isFavoriteExample.setColorFilter(Color.RED)
            } else {
                binding.isFavoriteExample.setColorFilter(Color.BLACK)
            }
        }
    }

    private fun changeSliderVisibility(newProgress: Int) {
        if (newProgress >= 50) {
            binding.themeCardSlider.visibility = View.VISIBLE
        } else {
            binding.themeCardSlider.visibility = View.GONE

        }
    }

    private fun checkUserInput(char: Char) {
        viewModel.check(char)
    }

    override fun onResume() {
        super.onResume()
        if (isNewFragment) {
            viewModel.prepareNextExample()
            isNewFragment = false
        } else {
            progressAnimation.changeRange(theme.progress)
            binding.themeCardProgress.startAnimation(progressAnimation)
        }
    }
}