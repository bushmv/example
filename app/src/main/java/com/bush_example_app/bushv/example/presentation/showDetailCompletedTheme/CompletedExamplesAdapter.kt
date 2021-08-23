package com.bush_example_app.bushv.example.presentation.showDetailCompletedTheme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bush_example_app.bushv.example.data.AppPref
import com.bush_example_app.bushv.example.databinding.RvItemCompletedExampleBinding
import com.bush_example_app.bushv.example.domain.entity.Example
import com.bush_example_app.bushv.example.utility.extensions.addForegroundSpan

class CompletedExamplesAdapter(private var examples: ArrayList<Example>) :
    RecyclerView.Adapter<CompletedExamplesAdapter.CompletedExampleViewHolder>() {

    fun changeExamples(examples: ArrayList<Example>) {
        this.examples = examples
        notifyDataSetChanged()
    }

    class CompletedExampleViewHolder(val binding: RvItemCompletedExampleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(example: Example) {
            binding.apply {
                word.text = example.word
                wordTranslate.text = example.wordTranslate
                exampleEN.text = example.sentenceEN.addForegroundSpan(AppPref.color)
                exampleRU.text = example.sentenceRU.addForegroundSpan(AppPref.color)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedExampleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemCompletedExampleBinding.inflate(layoutInflater, parent, false)
        return CompletedExampleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompletedExampleViewHolder, position: Int) {
        holder.bind(examples[position])
    }

    override fun getItemCount(): Int = examples.size

}