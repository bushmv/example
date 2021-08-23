package com.bush_example_app.bushv.example.presentation.showAppExplanation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bush_example_app.bushv.example.databinding.RvItemExplanationBinding

class ExplanationAdapter(private val explanations: ArrayList<Explanation>): RecyclerView.Adapter<ExplanationAdapter.ExplanationViewHolder>() {

    class ExplanationViewHolder(val binding: RvItemExplanationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(explanation: Explanation) {
            binding.apply {
                explanationDescription.text = explanation.description
                val context = binding.root.context
                val img = ResourcesCompat.getDrawable(context.resources, explanation.imageId, context.applicationContext.theme)
                explanationImage.setImageDrawable(img)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExplanationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemExplanationBinding.inflate(layoutInflater, parent, false)
        return ExplanationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExplanationViewHolder, position: Int) {
        holder.bind(explanations[position])
    }

    override fun getItemCount(): Int = explanations.size
}