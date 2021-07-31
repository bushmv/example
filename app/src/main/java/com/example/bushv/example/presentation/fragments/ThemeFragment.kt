package com.example.bushv.example.presentation.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.bushv.example.R
import com.example.bushv.example.databinding.FragThemeLayoutBinding
import com.example.bushv.example.domain.entity.Theme
import com.example.bushv.example.presentation.Keyboard
import com.example.bushv.example.presentation.ThemeViewModel
import com.example.bushv.example.presentation.ThemeViewModelFactory
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
        }
    }

    private fun checkUserInput(char: Char) {

    }

}