package me.zwsmith.datingapp.ui.matches

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.zwsmith.datingapp.R

class MatchListAdapter(
    private val context: Context
) : RecyclerView.Adapter<MatchListAdapter.MatchItem>() {
    var matchesData: List<MatchItemViewState> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MatchItem(view: View) : RecyclerView.ViewHolder(view) {
        val matchLayout: ConstraintLayout = view.findViewById(R.id.match_layout)
        val username: TextView = view.findViewById(R.id.username_tv)
        val age: TextView = view.findViewById(R.id.age_tv)
        val location: TextView = view.findViewById(R.id.location_tv)
        val matchPercent: TextView = view.findViewById(R.id.match_percent_tv)
        val profilePhoto: AppCompatImageView = view.findViewById(R.id.profile_photo_iv)
        val cancelButton: Button = view.findViewById(R.id.cancel_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchItem {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.match_card, parent, false)
        return MatchItem(view)
    }

    override fun getItemCount(): Int {
        return matchesData.size
    }

    override fun onBindViewHolder(holder: MatchItem, position: Int) {
        val viewState: MatchItemViewState = matchesData[position]
        with(holder) {
            matchLayout.setBackgroundColor(getSelectionStatusColor(viewState))
            cancelButton.isVisible = viewState.isCancelVisible
            cancelButton.setOnClickListener { viewState.onCancelClick() }
            itemView.setOnClickListener { matchesData[position].onClick() }
            username.text = viewState.username
            age.text = viewState.age
            location.text = getLocationString(viewState)
            matchPercent.text = context.getString(R.string.match_percent, viewState.matchPercent)
            Picasso.get()
                .load(viewState.imageUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_person)
                .into(profilePhoto)
        }
    }

    private fun getSelectionStatusColor(viewState: MatchItemViewState): Int {
        return ContextCompat.getColor(
            context,
            if (viewState.isYellow) R.color.yellow else R.color.colorAccent
        )
    }

    private fun getLocationString(viewState: MatchItemViewState) =
        context.getString(R.string.location, viewState.city, viewState.stateCode)
}