package me.zwsmith.datingapp.ui.matches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.zwsmith.datingapp.databinding.MatchCardBinding

class MatchListAdapter : RecyclerView.Adapter<MatchListAdapter.MatchItem>() {
    var matchesData: List<MatchItemViewState> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MatchItem(private val binding: MatchCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewState: MatchItemViewState) {
            binding.viewState = viewState
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchItem {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MatchCardBinding.inflate(layoutInflater, parent, false)
        return MatchItem(binding)
    }

    override fun getItemCount(): Int {
        return matchesData.size
    }

    override fun onBindViewHolder(holder: MatchItem, position: Int) {
        holder.bind(matchesData[position])
    }
}