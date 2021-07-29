package com.example.bushv.example.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bushv.example.databinding.FragSettingsBinding

class SettingsFragment: Fragment() {
    private lateinit var binding: FragSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
}