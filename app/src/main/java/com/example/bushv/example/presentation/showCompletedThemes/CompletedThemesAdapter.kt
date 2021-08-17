package com.example.bushv.example.presentation.showCompletedThemes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bushv.example.R
import com.example.bushv.example.databinding.RvItemCompletedThemeBinding
import com.example.bushv.example.domain.entity.Theme

class CompletedThemesAdapter(
    private var themes: ArrayList<Theme>,
    private val onClick: (Theme) -> Unit
) : RecyclerView.Adapter<CompletedThemesAdapter.CompletedThemesViewHolder>() {

    fun changeThemes(themes: ArrayList<Theme>) {
        this.themes = themes
        notifyDataSetChanged()
    }

    class CompletedThemesViewHolder(
        private val binding: RvItemCompletedThemeBinding,
        private val onClick: (Theme) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(theme: Theme) {
            binding.apply {
                completedThemeTitleEN.text = theme.titleEN()
                completedThemeTitleRU.text = theme.titleRU()
                showCompletedThemeDetails.text =
                    binding.root.resources.getQuantityString(
                        R.plurals.example_count_plurals_template, theme.examplesCount, theme.examplesCount)
                showCompletedThemeDetails.setOnClickListener { onClick(theme) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedThemesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemCompletedThemeBinding.inflate(inflater, parent, false)
        return CompletedThemesViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: CompletedThemesViewHolder, position: Int) {
        val theme = themes[position]
        holder.bind(theme)
    }

    override fun getItemCount(): Int = themes.size

}