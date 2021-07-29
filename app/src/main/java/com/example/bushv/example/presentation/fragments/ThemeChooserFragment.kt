package com.example.bushv.example.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bushv.example.databinding.FragThemeChooserBinding

class ThemeChooserFragment: Fragment() {

    private lateinit var binding: FragThemeChooserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragThemeChooserBinding.inflate(inflater, container, false)
        return binding.root
    }

}