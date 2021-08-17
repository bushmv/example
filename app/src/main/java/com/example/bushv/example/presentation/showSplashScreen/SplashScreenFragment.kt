package com.example.bushv.example.presentation.showSplashScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bushv.example.data.AppPref
import com.example.bushv.example.databinding.FragSplashScreenBinding
import java.util.*

class SplashScreenFragment: Fragment() {

    private lateinit var binding: FragSplashScreenBinding
    lateinit var closeSplashScreenCallback: () -> Unit
    private var firstStart = true
    private var list = arrayListOf(
        "В английском языке когда-то было на 3 буквы больше, чем сейчас. За долгие годы английский алфавит сократился! Сейчас в нем 26 букв.",
        "Наиболее часто используемые буквы английского алфавита — «E» и «T», наименее часто используемые — «Q» и «Z». А некоторое время в составе английского алфавита был ещё знак «&».",
        "Из всех слов в английском языке, «set» имеет наибольшее количество отдельных значений – по разным источникам число значений этого слова приближается к 200.",
        "Предложение «The quick brown fox jumps over the lazy dog» (быстрая коричневая лиса перепрыгивает через ленивую собаку) уникально тем, что здесь встречаются все буквы английского алфавита.",
        "Если в английском слове «очередь» (queue) убрать целых четыре последние буквы, то произношение не изменится",
        "В большинстве англоязычных стран есть свой собственный сленг, зачастую непонятный носителям английского из других стран.",
        "В 1962 году Международная организация гражданской авиации ИКАО (ICAO — International Civil Aviation Organization) ввела положения, согласно которым все пилоты и авиадиспетчеры должны говорить на английском.",
        "Самая сложная скороговорка в английском языке: The sixth sick sheik’s sixth sheep’s sick (Шестой больной шейх шестой больной овцы).",
        "Слово «pineapple» (ананас) состоит из двух слов: pine(сосна) и apple(яблоко).",
        "В английском есть название для ситуации, когда слушатели неправильно разбирают слова в песне — mondegreen"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnClickListener {
            closeSplashScreenCallback()
        }
        binding.cbNoShowFactsMore.setOnCheckedChangeListener { _, isChecked -> AppPref.showSplashScreen = !isChecked }
        val number = Random().nextInt(list.size)
        binding.tvInterestingFactNumber.text = "интересный факт #${number + 1}"
        binding.tvInterestingFact.text = list[number]
        firstStart = false
    }

    // when MainActivity complete loading examples from db in background tread
    // this method will be call from UI thread
    fun loadingHasCompleted() {
        binding.apply {
            splashProgressBar.visibility = View.INVISIBLE
            splashTapToCloseSplashText.visibility = View.VISIBLE
        }
    }

    fun close() {
        closeSplashScreenCallback()
    }

}