package com.bush_example_app.bushv.example.presentation.showSavedExamples

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bush_example_app.bushv.example.data.AppPref
import com.bush_example_app.bushv.example.databinding.RvItemSavedExampleBinding
import com.bush_example_app.bushv.example.domain.entity.Example
import com.bush_example_app.bushv.example.utility.extensions.addForegroundSpan

class SavedExamplesAdapter(
    private var allExamples: ArrayList<Example>,
    private var filteredExamples: ArrayList<Example> = ArrayList(allExamples)
) :
    RecyclerView.Adapter<SavedExamplesAdapter.SavedExamplesViewHolder>(), Filterable {

    fun changeData(favoriteExamples: ArrayList<Example>) {
        this.allExamples.clear()
        this.allExamples.addAll(favoriteExamples)
        this.filteredExamples.clear()
        this.filteredExamples.addAll(favoriteExamples)
        notifyDataSetChanged()
    }

    class SavedExamplesViewHolder(val binding: RvItemSavedExampleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(example: Example) {
            binding.apply {
                exampleEN.text =
                    example.sentenceEN.addForegroundSpan(Color.parseColor(AppPref.colorStr))
                exampleRU.text =
                    example.sentenceRU.addForegroundSpan(Color.parseColor(AppPref.colorStr))
                word.text = example.word
                wordTranslate.text = example.wordTranslate
                changeColor(example)
                isFavorite.setOnClickListener {
                    example.isFavorite = !example.isFavorite
                    changeColor(example)
                }
            }
        }

        private fun changeColor(example: Example) {
            if (example.isFavorite) {
                binding.isFavorite.scaleX = 0f
                binding.isFavorite.scaleY = 0f
                binding.isFavorite.setColorFilter(Color.RED)
                binding.isFavorite.animate()
                    .scaleX(1.3f)
                    .scaleY(1.3f)
                    .setDuration(300)
                    .withEndAction {
                        binding.isFavorite.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(200)
                    }.start()

            } else {
                binding.isFavorite.setColorFilter(Color.BLACK)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedExamplesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemSavedExampleBinding.inflate(inflater, parent, false)
        return SavedExamplesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedExamplesViewHolder, position: Int) {
        val example = filteredExamples[position]
        holder.bind(example)
    }

    override fun getItemCount(): Int = filteredExamples.size

    override fun getFilter(): Filter = _filter

    private val _filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {

            val filterResults = FilterResults()
            if (constraint.isEmpty()) {
                filterResults.values = allExamples
            } else {
                val pattern = constraint.toString().lowercase().trim()
                val result = arrayListOf<Example>()
                allExamples.forEach { if (it.word.lowercase().contains(pattern)) result.add(it) }
                filterResults.values = result
//                    allExamples.filter { it.word
//                        .lowercase().contains(pattern) }
            }
            return filterResults
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            filteredExamples.clear()
            filteredExamples.addAll(results.values as ArrayList<Example>)
            notifyDataSetChanged()
        }
    }
}