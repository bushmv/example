package com.example.bushv.example.presentation.recyclerAdapters

import android.graphics.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.bushv.example.R
import com.example.bushv.example.databinding.RvItemThemeCardBinding
import com.example.bushv.example.domain.entity.Theme
import java.util.*

class ThemeViewPagerAdapter(
    private var themes: ArrayList<Theme>,
    private val onClick: (Pair<TextView, CardView>, Theme) -> Unit
) :
    RecyclerView.Adapter<ThemeViewPagerAdapter.ThemeViewHolder>() {

    class ThemeViewHolder(private val binding: RvItemThemeCardBinding) : RecyclerView.ViewHolder(binding.root) {
        private var offset: Float = 0f
        fun bind(theme: Theme, onClick: (Pair<TextView, CardView>, Theme) -> Unit) {
            binding.apply {

                // transition
                themeCard.themeCard.transitionName = theme.themeId.toString()
                themeCard.themeTitle.transitionName = theme.title

                // set field
                val resources = this.root.context.resources
                themeCard.apply {
                    themeTitle.text = theme.title.split("/")[0]
                    themeExamplesCount.text =
                        resources.getQuantityString(R.plurals.example_count_plurals_template, theme.wordCount, theme.wordCount)
                    themeEnglishLevel.text = theme.level.name
                    themeTimeToComplete.text = resources.getString(R.string.time_to_complete, theme.timeToComplete)
                    themeProgress.text = resources.getString(R.string.theme_progress, theme.progress)
                    moreInfoTitle.text = theme.title.split("/")[1]
                    moreInfoExplanation.text = theme.info
                    val englishLevelBackground = Color.parseColor(theme.level.strColor)
                    themeEnglishLevel.background.colorFilter = PorterDuffColorFilter(englishLevelBackground, PorterDuff.Mode.SRC_ATOP)
                }

                // onClickListeners
                themeCard.showThemeInfo.setOnClickListener { showThemeDescription() }
                moreInfoBack.setOnClickListener { hideThemeDescription() }
                themeCard.startTheme.setOnClickListener {
                    onClick(Pair(themeCard.themeTitle, themeCard.themeCard), theme)
                }
            }
        }

        private fun showThemeDescription() {
            binding.themeCard.showThemeInfo.isClickable = false
            binding.moreInfoBack.isClickable = true
            offset = (binding.root.height - binding.themeCard.themeCard.y) * 0.8f
            translationYAnimation(binding.themeCard.themeCard, offset, 500)
            scaleAnimation(binding.themeCard.themeCard, 0.8f, 0.8f, 500)
            alphaAnimation(binding.moreInfoSection, 1f, 750)
        }

        private fun hideThemeDescription() {
            binding.themeCard.showThemeInfo.isClickable = true
            binding.moreInfoBack.isClickable = false
            translationYAnimation(binding.themeCard.themeCard, -offset, 300)
            scaleAnimation(binding.themeCard.themeCard, 1f, 1f, 500)
            alphaAnimation(binding.moreInfoSection, 0f, 500)
        }

        private fun translationYAnimation(view: View, offset: Float, duration: Long) {
            view.animate()
                .translationYBy(offset)
                .setDuration(duration)
                .start()
        }
        private fun scaleAnimation(view: View, scaleX: Float, scaleY: Float, duration: Long) {
            view.animate()
                .scaleX(scaleX)
                .scaleY(scaleY)
                .setDuration(duration)
                .start()
        }
        private fun alphaAnimation(view: View, alpha: Float, duration: Long) {
            view.animate()
                .alpha(alpha)
                .setDuration(duration)
                .start()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemThemeCardBinding.inflate(inflater, parent, false)
        return ThemeViewHolder(binding)
    }

    override fun getItemCount(): Int  = themes.size

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        val theme = themes[position]
        holder.bind(theme, onClick)
    }

}